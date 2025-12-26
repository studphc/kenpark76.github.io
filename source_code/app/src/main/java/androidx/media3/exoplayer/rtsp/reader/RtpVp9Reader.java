package androidx.media3.exoplayer.rtsp.reader;

import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Log;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.common.util.Util;
import androidx.media3.exoplayer.rtsp.RtpPacket;
import androidx.media3.exoplayer.rtsp.RtpPayloadFormat;
import androidx.media3.extractor.ExtractorOutput;
import androidx.media3.extractor.TrackOutput;
/* loaded from: classes.dex */
final class RtpVp9Reader implements RtpPayloadReader {
    private static final int MEDIA_CLOCK_FREQUENCY = 90000;
    private static final int SCALABILITY_STRUCTURE_SIZE = 4;
    private static final String TAG = "RtpVp9Reader";
    private boolean gotFirstPacketOfVp9Frame;
    private boolean isKeyFrame;
    private final RtpPayloadFormat payloadFormat;
    private boolean reportedOutputFormat;
    private TrackOutput trackOutput;
    private long firstReceivedTimestamp = -9223372036854775807L;
    private int fragmentedSampleSizeBytes = -1;
    private long fragmentedSampleTimeUs = -9223372036854775807L;
    private long startTimeOffsetUs = 0;
    private int previousSequenceNumber = -1;
    private int width = -1;
    private int height = -1;

    public RtpVp9Reader(RtpPayloadFormat rtpPayloadFormat) {
        this.payloadFormat = rtpPayloadFormat;
    }

    @Override // androidx.media3.exoplayer.rtsp.reader.RtpPayloadReader
    public void createTracks(ExtractorOutput extractorOutput, int i) {
        TrackOutput track = extractorOutput.track(i, 2);
        this.trackOutput = track;
        track.format(this.payloadFormat.format);
    }

    @Override // androidx.media3.exoplayer.rtsp.reader.RtpPayloadReader
    public void onReceivingFirstPacket(long j, int i) {
        Assertions.checkState(this.firstReceivedTimestamp == -9223372036854775807L);
        this.firstReceivedTimestamp = j;
    }

    @Override // androidx.media3.exoplayer.rtsp.reader.RtpPayloadReader
    public void consume(ParsableByteArray parsableByteArray, long j, int i, boolean z) {
        int i2;
        Assertions.checkStateNotNull(this.trackOutput);
        if (validateVp9Descriptor(parsableByteArray, i)) {
            if (this.fragmentedSampleSizeBytes == -1 && this.gotFirstPacketOfVp9Frame) {
                this.isKeyFrame = (parsableByteArray.peekUnsignedByte() & 4) == 0;
            }
            if (!this.reportedOutputFormat && (i2 = this.width) != -1 && this.height != -1) {
                if (i2 != this.payloadFormat.format.width || this.height != this.payloadFormat.format.height) {
                    this.trackOutput.format(this.payloadFormat.format.buildUpon().setWidth(this.width).setHeight(this.height).build());
                }
                this.reportedOutputFormat = true;
            }
            int bytesLeft = parsableByteArray.bytesLeft();
            this.trackOutput.sampleData(parsableByteArray, bytesLeft);
            int i3 = this.fragmentedSampleSizeBytes;
            if (i3 == -1) {
                this.fragmentedSampleSizeBytes = bytesLeft;
            } else {
                this.fragmentedSampleSizeBytes = i3 + bytesLeft;
            }
            this.fragmentedSampleTimeUs = RtpReaderUtils.toSampleTimeUs(this.startTimeOffsetUs, j, this.firstReceivedTimestamp, MEDIA_CLOCK_FREQUENCY);
            if (z) {
                outputSampleMetadataForFragmentedPackets();
            }
            this.previousSequenceNumber = i;
        }
    }

    @Override // androidx.media3.exoplayer.rtsp.reader.RtpPayloadReader
    public void seek(long j, long j2) {
        this.firstReceivedTimestamp = j;
        this.fragmentedSampleSizeBytes = -1;
        this.startTimeOffsetUs = j2;
    }

    private boolean validateVp9Descriptor(ParsableByteArray parsableByteArray, int i) {
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        if ((readUnsignedByte & 8) == 8) {
            if (this.gotFirstPacketOfVp9Frame && this.fragmentedSampleSizeBytes > 0) {
                outputSampleMetadataForFragmentedPackets();
            }
            this.gotFirstPacketOfVp9Frame = true;
        } else if (this.gotFirstPacketOfVp9Frame) {
            int nextSequenceNumber = RtpPacket.getNextSequenceNumber(this.previousSequenceNumber);
            if (i < nextSequenceNumber) {
                Log.w(TAG, Util.formatInvariant("Received RTP packet with unexpected sequence number. Expected: %d; received: %d. Dropping packet.", Integer.valueOf(nextSequenceNumber), Integer.valueOf(i)));
                return false;
            }
        } else {
            Log.w(TAG, "First payload octet of the RTP packet is not the beginning of a new VP9 partition, Dropping current packet.");
            return false;
        }
        if ((readUnsignedByte & 128) == 0 || (parsableByteArray.readUnsignedByte() & 128) == 0 || parsableByteArray.bytesLeft() >= 1) {
            int i2 = readUnsignedByte & 16;
            Assertions.checkArgument(i2 == 0, "VP9 flexible mode is not supported.");
            if ((readUnsignedByte & 32) != 0) {
                parsableByteArray.skipBytes(1);
                if (parsableByteArray.bytesLeft() < 1) {
                    return false;
                }
                if (i2 == 0) {
                    parsableByteArray.skipBytes(1);
                }
            }
            if ((readUnsignedByte & 2) != 0) {
                int readUnsignedByte2 = parsableByteArray.readUnsignedByte();
                int i3 = (readUnsignedByte2 >> 5) & 7;
                if ((readUnsignedByte2 & 16) != 0) {
                    int i4 = i3 + 1;
                    if (parsableByteArray.bytesLeft() < i4 * 4) {
                        return false;
                    }
                    for (int i5 = 0; i5 < i4; i5++) {
                        this.width = parsableByteArray.readUnsignedShort();
                        this.height = parsableByteArray.readUnsignedShort();
                    }
                }
                if ((readUnsignedByte2 & 8) != 0) {
                    int readUnsignedByte3 = parsableByteArray.readUnsignedByte();
                    if (parsableByteArray.bytesLeft() < readUnsignedByte3) {
                        return false;
                    }
                    for (int i6 = 0; i6 < readUnsignedByte3; i6++) {
                        int readUnsignedShort = (parsableByteArray.readUnsignedShort() & 12) >> 2;
                        if (parsableByteArray.bytesLeft() < readUnsignedShort) {
                            return false;
                        }
                        parsableByteArray.skipBytes(readUnsignedShort);
                    }
                }
            }
            return true;
        }
        return false;
    }

    private void outputSampleMetadataForFragmentedPackets() {
        long j = this.fragmentedSampleTimeUs;
        boolean z = this.isKeyFrame;
        ((TrackOutput) Assertions.checkNotNull(this.trackOutput)).sampleMetadata(j, z ? 1 : 0, this.fragmentedSampleSizeBytes, 0, null);
        this.fragmentedSampleSizeBytes = -1;
        this.fragmentedSampleTimeUs = -9223372036854775807L;
        this.gotFirstPacketOfVp9Frame = false;
    }
}

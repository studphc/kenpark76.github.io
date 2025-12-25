package androidx.media3.extractor.mp4;

import androidx.media3.common.C;
import androidx.media3.common.Metadata;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.extractor.metadata.mp4.SmtaMetadataEntry;
/* loaded from: classes.dex */
public final class SmtaAtomUtil {
    private static final int CAMCORDER_FRC_SUPERSLOW_MOTION = 9;
    private static final int CAMCORDER_FRC_SUPERSLOW_MOTION_HEVC = 22;
    private static final int CAMCORDER_NORMAL = 0;
    private static final int CAMCORDER_QFRC_SUPERSLOW_MOTION = 23;
    private static final int CAMCORDER_SINGLE_SUPERSLOW_MOTION = 7;
    private static final int CAMCORDER_SLOW_MOTION_V2 = 12;
    private static final int CAMCORDER_SLOW_MOTION_V2_120 = 13;
    private static final int CAMCORDER_SLOW_MOTION_V2_HEVC = 21;
    private static final int NO_VALUE = -1;

    private SmtaAtomUtil() {
    }

    public static Metadata parseSmta(ParsableByteArray parsableByteArray, int i) {
        parsableByteArray.skipBytes(12);
        while (parsableByteArray.getPosition() < i) {
            int position = parsableByteArray.getPosition();
            int readInt = parsableByteArray.readInt();
            if (parsableByteArray.readInt() == 1935766900) {
                if (readInt < 16) {
                    return null;
                }
                parsableByteArray.skipBytes(4);
                int i2 = -1;
                int i3 = 0;
                for (int i4 = 0; i4 < 2; i4++) {
                    int readUnsignedByte = parsableByteArray.readUnsignedByte();
                    int readUnsignedByte2 = parsableByteArray.readUnsignedByte();
                    if (readUnsignedByte == 0) {
                        i2 = readUnsignedByte2;
                    } else if (readUnsignedByte == 1) {
                        i3 = readUnsignedByte2;
                    }
                }
                int captureFrameRate = getCaptureFrameRate(i2, parsableByteArray, i);
                if (captureFrameRate == -2147483647) {
                    return null;
                }
                return new Metadata(new SmtaMetadataEntry(captureFrameRate, i3));
            }
            parsableByteArray.setPosition(position + readInt);
        }
        return null;
    }

    private static int getCaptureFrameRate(int i, ParsableByteArray parsableByteArray, int i2) {
        if (i == 12) {
            return 240;
        }
        if (i == 13) {
            return 120;
        }
        if (i == 21 && parsableByteArray.bytesLeft() >= 8 && parsableByteArray.getPosition() + 8 <= i2) {
            int readInt = parsableByteArray.readInt();
            int readInt2 = parsableByteArray.readInt();
            if (readInt >= 12 && readInt2 == 1936877170) {
                return parsableByteArray.readUnsignedFixedPoint1616();
            }
        }
        return C.RATE_UNSET_INT;
    }
}

package androidx.media3.extractor.mp4;

import android.util.Pair;
import androidx.media3.common.C;
import androidx.media3.common.ColorInfo;
import androidx.media3.common.DrmInitData;
import androidx.media3.common.Format;
import androidx.media3.common.Metadata;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.ParserException;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.CodecSpecificDataUtil;
import androidx.media3.common.util.Log;
import androidx.media3.common.util.ParsableBitArray;
import androidx.media3.common.util.ParsableByteArray;
import androidx.media3.common.util.Util;
import androidx.media3.container.MdtaMetadataEntry;
import androidx.media3.container.Mp4LocationData;
import androidx.media3.container.Mp4TimestampData;
import androidx.media3.extractor.AacUtil;
import androidx.media3.extractor.Ac3Util;
import androidx.media3.extractor.Ac4Util;
import androidx.media3.extractor.AvcConfig;
import androidx.media3.extractor.DolbyVisionConfig;
import androidx.media3.extractor.ExtractorUtil;
import androidx.media3.extractor.GaplessInfoHolder;
import androidx.media3.extractor.HevcConfig;
import androidx.media3.extractor.OpusUtil;
import androidx.media3.extractor.VorbisUtil;
import androidx.media3.extractor.mp4.Atom;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Ints;
import j$.util.Objects;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlinx.coroutines.scheduling.WorkQueueKt;
/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class AtomParsers {
    private static final int MAX_GAPLESS_TRIM_SIZE_SAMPLES = 4;
    private static final String TAG = "AtomParsers";
    private static final int TYPE_clcp = 1668047728;
    private static final int TYPE_mdta = 1835299937;
    private static final int TYPE_meta = 1835365473;
    private static final int TYPE_nclc = 1852009571;
    private static final int TYPE_nclx = 1852009592;
    private static final int TYPE_sbtl = 1935832172;
    private static final int TYPE_soun = 1936684398;
    private static final int TYPE_subt = 1937072756;
    private static final int TYPE_text = 1952807028;
    private static final int TYPE_vide = 1986618469;
    private static final byte[] opusMagic = Util.getUtf8Bytes("OpusHead");

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public interface SampleSizeBox {
        int getFixedSampleSize();

        int getSampleCount();

        int readNextSampleSize();
    }

    private static boolean canTrimSamplesWithTimestampChange(int i) {
        return i != 1;
    }

    private static int getTrackTypeForHdlr(int i) {
        if (i == TYPE_soun) {
            return 1;
        }
        if (i == TYPE_vide) {
            return 2;
        }
        if (i == TYPE_text || i == TYPE_sbtl || i == TYPE_subt || i == TYPE_clcp) {
            return 3;
        }
        return i == 1835365473 ? 5 : -1;
    }

    public static List<TrackSampleTable> parseTraks(Atom.ContainerAtom containerAtom, GaplessInfoHolder gaplessInfoHolder, long j, DrmInitData drmInitData, boolean z, boolean z2, Function<Track, Track> function) throws ParserException {
        Track apply;
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < containerAtom.containerChildren.size(); i++) {
            Atom.ContainerAtom containerAtom2 = containerAtom.containerChildren.get(i);
            if (containerAtom2.type == 1953653099 && (apply = function.apply(parseTrak(containerAtom2, (Atom.LeafAtom) Assertions.checkNotNull(containerAtom.getLeafAtomOfType(1836476516)), j, drmInitData, z, z2))) != null) {
                arrayList.add(parseStbl(apply, (Atom.ContainerAtom) Assertions.checkNotNull(((Atom.ContainerAtom) Assertions.checkNotNull(((Atom.ContainerAtom) Assertions.checkNotNull(containerAtom2.getContainerAtomOfType(1835297121))).getContainerAtomOfType(1835626086))).getContainerAtomOfType(1937007212)), gaplessInfoHolder));
            }
        }
        return arrayList;
    }

    public static Metadata parseUdta(Atom.LeafAtom leafAtom) {
        ParsableByteArray parsableByteArray = leafAtom.data;
        parsableByteArray.setPosition(8);
        Metadata metadata = new Metadata(new Metadata.Entry[0]);
        while (parsableByteArray.bytesLeft() >= 8) {
            int position = parsableByteArray.getPosition();
            int readInt = parsableByteArray.readInt();
            int readInt2 = parsableByteArray.readInt();
            if (readInt2 == 1835365473) {
                parsableByteArray.setPosition(position);
                metadata = metadata.copyWithAppendedEntriesFrom(parseUdtaMeta(parsableByteArray, position + readInt));
            } else if (readInt2 == 1936553057) {
                parsableByteArray.setPosition(position);
                metadata = metadata.copyWithAppendedEntriesFrom(SmtaAtomUtil.parseSmta(parsableByteArray, position + readInt));
            } else if (readInt2 == -1451722374) {
                metadata = metadata.copyWithAppendedEntriesFrom(parseXyz(parsableByteArray));
            }
            parsableByteArray.setPosition(position + readInt);
        }
        return metadata;
    }

    public static Mp4TimestampData parseMvhd(ParsableByteArray parsableByteArray) {
        long readLong;
        long readLong2;
        parsableByteArray.setPosition(8);
        if (Atom.parseFullAtomVersion(parsableByteArray.readInt()) == 0) {
            readLong = parsableByteArray.readUnsignedInt();
            readLong2 = parsableByteArray.readUnsignedInt();
        } else {
            readLong = parsableByteArray.readLong();
            readLong2 = parsableByteArray.readLong();
        }
        return new Mp4TimestampData(readLong, readLong2, parsableByteArray.readUnsignedInt());
    }

    public static Metadata parseMdtaFromMeta(Atom.ContainerAtom containerAtom) {
        Atom.LeafAtom leafAtomOfType = containerAtom.getLeafAtomOfType(1751411826);
        Atom.LeafAtom leafAtomOfType2 = containerAtom.getLeafAtomOfType(1801812339);
        Atom.LeafAtom leafAtomOfType3 = containerAtom.getLeafAtomOfType(1768715124);
        if (leafAtomOfType == null || leafAtomOfType2 == null || leafAtomOfType3 == null || parseHdlr(leafAtomOfType.data) != TYPE_mdta) {
            return null;
        }
        ParsableByteArray parsableByteArray = leafAtomOfType2.data;
        parsableByteArray.setPosition(12);
        int readInt = parsableByteArray.readInt();
        String[] strArr = new String[readInt];
        for (int i = 0; i < readInt; i++) {
            int readInt2 = parsableByteArray.readInt();
            parsableByteArray.skipBytes(4);
            strArr[i] = parsableByteArray.readString(readInt2 - 8);
        }
        ParsableByteArray parsableByteArray2 = leafAtomOfType3.data;
        parsableByteArray2.setPosition(8);
        ArrayList arrayList = new ArrayList();
        while (parsableByteArray2.bytesLeft() > 8) {
            int position = parsableByteArray2.getPosition();
            int readInt3 = parsableByteArray2.readInt();
            int readInt4 = parsableByteArray2.readInt() - 1;
            if (readInt4 >= 0 && readInt4 < readInt) {
                MdtaMetadataEntry parseMdtaMetadataEntryFromIlst = MetadataUtil.parseMdtaMetadataEntryFromIlst(parsableByteArray2, position + readInt3, strArr[readInt4]);
                if (parseMdtaMetadataEntryFromIlst != null) {
                    arrayList.add(parseMdtaMetadataEntryFromIlst);
                }
            } else {
                Log.w(TAG, "Skipped metadata with unknown key index: " + readInt4);
            }
            parsableByteArray2.setPosition(position + readInt3);
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return new Metadata(arrayList);
    }

    public static void maybeSkipRemainingMetaAtomHeaderBytes(ParsableByteArray parsableByteArray) {
        int position = parsableByteArray.getPosition();
        parsableByteArray.skipBytes(4);
        if (parsableByteArray.readInt() != 1751411826) {
            position += 4;
        }
        parsableByteArray.setPosition(position);
    }

    private static Track parseTrak(Atom.ContainerAtom containerAtom, Atom.LeafAtom leafAtom, long j, DrmInitData drmInitData, boolean z, boolean z2) throws ParserException {
        Atom.LeafAtom leafAtom2;
        long j2;
        long[] jArr;
        long[] jArr2;
        Atom.ContainerAtom containerAtomOfType;
        Pair<long[], long[]> parseEdts;
        Atom.ContainerAtom containerAtom2 = (Atom.ContainerAtom) Assertions.checkNotNull(containerAtom.getContainerAtomOfType(1835297121));
        int trackTypeForHdlr = getTrackTypeForHdlr(parseHdlr(((Atom.LeafAtom) Assertions.checkNotNull(containerAtom2.getLeafAtomOfType(1751411826))).data));
        if (trackTypeForHdlr == -1) {
            return null;
        }
        TkhdData parseTkhd = parseTkhd(((Atom.LeafAtom) Assertions.checkNotNull(containerAtom.getLeafAtomOfType(1953196132))).data);
        if (j == -9223372036854775807L) {
            leafAtom2 = leafAtom;
            j2 = parseTkhd.duration;
        } else {
            leafAtom2 = leafAtom;
            j2 = j;
        }
        long j3 = parseMvhd(leafAtom2.data).timescale;
        long scaleLargeTimestamp = j2 != -9223372036854775807L ? Util.scaleLargeTimestamp(j2, 1000000L, j3) : -9223372036854775807L;
        Pair<Long, String> parseMdhd = parseMdhd(((Atom.LeafAtom) Assertions.checkNotNull(containerAtom2.getLeafAtomOfType(1835296868))).data);
        Atom.LeafAtom leafAtomOfType = ((Atom.ContainerAtom) Assertions.checkNotNull(((Atom.ContainerAtom) Assertions.checkNotNull(containerAtom2.getContainerAtomOfType(1835626086))).getContainerAtomOfType(1937007212))).getLeafAtomOfType(1937011556);
        if (leafAtomOfType == null) {
            throw ParserException.createForMalformedContainer("Malformed sample table (stbl) missing sample description (stsd)", null);
        }
        StsdData parseStsd = parseStsd(leafAtomOfType.data, parseTkhd.id, parseTkhd.rotationDegrees, (String) parseMdhd.second, drmInitData, z2);
        if (z || (containerAtomOfType = containerAtom.getContainerAtomOfType(1701082227)) == null || (parseEdts = parseEdts(containerAtomOfType)) == null) {
            jArr = null;
            jArr2 = null;
        } else {
            jArr2 = (long[]) parseEdts.second;
            jArr = (long[]) parseEdts.first;
        }
        if (parseStsd.format == null) {
            return null;
        }
        return new Track(parseTkhd.id, trackTypeForHdlr, ((Long) parseMdhd.first).longValue(), j3, scaleLargeTimestamp, parseStsd.format, parseStsd.requiredSampleTransformation, parseStsd.trackEncryptionBoxes, parseStsd.nalUnitLengthFieldLength, jArr, jArr2);
    }

    /* JADX WARN: Removed duplicated region for block: B:116:0x02ae  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x02be  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x03b3  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x03b5  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x03d1  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x043e  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x0443  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x0446  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x044a  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x044d  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x0450  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x0452  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x0456  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x0459  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x0466  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0135  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static androidx.media3.extractor.mp4.TrackSampleTable parseStbl(androidx.media3.extractor.mp4.Track r37, androidx.media3.extractor.mp4.Atom.ContainerAtom r38, androidx.media3.extractor.GaplessInfoHolder r39) throws androidx.media3.common.ParserException {
        /*
            Method dump skipped, instructions count: 1335
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.media3.extractor.mp4.AtomParsers.parseStbl(androidx.media3.extractor.mp4.Track, androidx.media3.extractor.mp4.Atom$ContainerAtom, androidx.media3.extractor.GaplessInfoHolder):androidx.media3.extractor.mp4.TrackSampleTable");
    }

    private static Metadata parseUdtaMeta(ParsableByteArray parsableByteArray, int i) {
        parsableByteArray.skipBytes(8);
        maybeSkipRemainingMetaAtomHeaderBytes(parsableByteArray);
        while (parsableByteArray.getPosition() < i) {
            int position = parsableByteArray.getPosition();
            int readInt = parsableByteArray.readInt();
            if (parsableByteArray.readInt() == 1768715124) {
                parsableByteArray.setPosition(position);
                return parseIlst(parsableByteArray, position + readInt);
            }
            parsableByteArray.setPosition(position + readInt);
        }
        return null;
    }

    private static Metadata parseIlst(ParsableByteArray parsableByteArray, int i) {
        parsableByteArray.skipBytes(8);
        ArrayList arrayList = new ArrayList();
        while (parsableByteArray.getPosition() < i) {
            Metadata.Entry parseIlstElement = MetadataUtil.parseIlstElement(parsableByteArray);
            if (parseIlstElement != null) {
                arrayList.add(parseIlstElement);
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return new Metadata(arrayList);
    }

    private static Metadata parseXyz(ParsableByteArray parsableByteArray) {
        short readShort = parsableByteArray.readShort();
        parsableByteArray.skipBytes(2);
        String readString = parsableByteArray.readString(readShort);
        int max = Math.max(readString.lastIndexOf(43), readString.lastIndexOf(45));
        try {
            return new Metadata(new Mp4LocationData(Float.parseFloat(readString.substring(0, max)), Float.parseFloat(readString.substring(max, readString.length() - 1))));
        } catch (IndexOutOfBoundsException | NumberFormatException unused) {
            return null;
        }
    }

    private static TkhdData parseTkhd(ParsableByteArray parsableByteArray) {
        boolean z;
        parsableByteArray.setPosition(8);
        int parseFullAtomVersion = Atom.parseFullAtomVersion(parsableByteArray.readInt());
        parsableByteArray.skipBytes(parseFullAtomVersion == 0 ? 8 : 16);
        int readInt = parsableByteArray.readInt();
        parsableByteArray.skipBytes(4);
        int position = parsableByteArray.getPosition();
        int i = parseFullAtomVersion == 0 ? 4 : 8;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i3 >= i) {
                z = true;
                break;
            } else if (parsableByteArray.getData()[position + i3] != -1) {
                z = false;
                break;
            } else {
                i3++;
            }
        }
        long j = -9223372036854775807L;
        if (z) {
            parsableByteArray.skipBytes(i);
        } else {
            long readUnsignedInt = parseFullAtomVersion == 0 ? parsableByteArray.readUnsignedInt() : parsableByteArray.readUnsignedLongToLong();
            if (readUnsignedInt != 0) {
                j = readUnsignedInt;
            }
        }
        parsableByteArray.skipBytes(16);
        int readInt2 = parsableByteArray.readInt();
        int readInt3 = parsableByteArray.readInt();
        parsableByteArray.skipBytes(4);
        int readInt4 = parsableByteArray.readInt();
        int readInt5 = parsableByteArray.readInt();
        if (readInt2 == 0 && readInt3 == 65536 && readInt4 == -65536 && readInt5 == 0) {
            i2 = 90;
        } else if (readInt2 == 0 && readInt3 == -65536 && readInt4 == 65536 && readInt5 == 0) {
            i2 = 270;
        } else if (readInt2 == -65536 && readInt3 == 0 && readInt4 == 0 && readInt5 == -65536) {
            i2 = 180;
        }
        return new TkhdData(readInt, j, i2);
    }

    private static int parseHdlr(ParsableByteArray parsableByteArray) {
        parsableByteArray.setPosition(16);
        return parsableByteArray.readInt();
    }

    private static Pair<Long, String> parseMdhd(ParsableByteArray parsableByteArray) {
        parsableByteArray.setPosition(8);
        int parseFullAtomVersion = Atom.parseFullAtomVersion(parsableByteArray.readInt());
        parsableByteArray.skipBytes(parseFullAtomVersion == 0 ? 8 : 16);
        long readUnsignedInt = parsableByteArray.readUnsignedInt();
        parsableByteArray.skipBytes(parseFullAtomVersion == 0 ? 4 : 8);
        int readUnsignedShort = parsableByteArray.readUnsignedShort();
        return Pair.create(Long.valueOf(readUnsignedInt), "" + ((char) (((readUnsignedShort >> 10) & 31) + 96)) + ((char) (((readUnsignedShort >> 5) & 31) + 96)) + ((char) ((readUnsignedShort & 31) + 96)));
    }

    private static StsdData parseStsd(ParsableByteArray parsableByteArray, int i, int i2, String str, DrmInitData drmInitData, boolean z) throws ParserException {
        int i3;
        parsableByteArray.setPosition(12);
        int readInt = parsableByteArray.readInt();
        StsdData stsdData = new StsdData(readInt);
        for (int i4 = 0; i4 < readInt; i4++) {
            int position = parsableByteArray.getPosition();
            int readInt2 = parsableByteArray.readInt();
            ExtractorUtil.checkContainerInput(readInt2 > 0, "childAtomSize must be positive");
            int readInt3 = parsableByteArray.readInt();
            if (readInt3 == 1635148593 || readInt3 == 1635148595 || readInt3 == 1701733238 || readInt3 == 1831958048 || readInt3 == 1836070006 || readInt3 == 1752589105 || readInt3 == 1751479857 || readInt3 == 1932670515 || readInt3 == 1211250227 || readInt3 == 1987063864 || readInt3 == 1987063865 || readInt3 == 1635135537 || readInt3 == 1685479798 || readInt3 == 1685479729 || readInt3 == 1685481573 || readInt3 == 1685481521) {
                i3 = position;
                parseVideoSampleEntry(parsableByteArray, readInt3, i3, readInt2, i, i2, drmInitData, stsdData, i4);
            } else if (readInt3 == 1836069985 || readInt3 == 1701733217 || readInt3 == 1633889587 || readInt3 == 1700998451 || readInt3 == 1633889588 || readInt3 == 1835823201 || readInt3 == 1685353315 || readInt3 == 1685353317 || readInt3 == 1685353320 || readInt3 == 1685353324 || readInt3 == 1685353336 || readInt3 == 1935764850 || readInt3 == 1935767394 || readInt3 == 1819304813 || readInt3 == 1936684916 || readInt3 == 1953984371 || readInt3 == 778924082 || readInt3 == 778924083 || readInt3 == 1835557169 || readInt3 == 1835560241 || readInt3 == 1634492771 || readInt3 == 1634492791 || readInt3 == 1970037111 || readInt3 == 1332770163 || readInt3 == 1716281667) {
                i3 = position;
                parseAudioSampleEntry(parsableByteArray, readInt3, position, readInt2, i, str, z, drmInitData, stsdData, i4);
            } else {
                if (readInt3 == 1414810956 || readInt3 == 1954034535 || readInt3 == 2004251764 || readInt3 == 1937010800 || readInt3 == 1664495672) {
                    parseTextSampleEntry(parsableByteArray, readInt3, position, readInt2, i, str, stsdData);
                } else if (readInt3 == 1835365492) {
                    parseMetaDataSampleEntry(parsableByteArray, readInt3, position, i, stsdData);
                } else if (readInt3 == 1667329389) {
                    stsdData.format = new Format.Builder().setId(i).setSampleMimeType("application/x-camera-motion").build();
                }
                i3 = position;
            }
            parsableByteArray.setPosition(i3 + readInt2);
        }
        return stsdData;
    }

    private static void parseTextSampleEntry(ParsableByteArray parsableByteArray, int i, int i2, int i3, int i4, String str, StsdData stsdData) {
        parsableByteArray.setPosition(i2 + 8 + 8);
        String str2 = "application/ttml+xml";
        ImmutableList immutableList = null;
        long j = Long.MAX_VALUE;
        if (i != 1414810956) {
            if (i == 1954034535) {
                int i5 = (i3 - 8) - 8;
                byte[] bArr = new byte[i5];
                parsableByteArray.readBytes(bArr, 0, i5);
                immutableList = ImmutableList.of(bArr);
                str2 = "application/x-quicktime-tx3g";
            } else if (i == 2004251764) {
                str2 = "application/x-mp4-vtt";
            } else if (i == 1937010800) {
                j = 0;
            } else if (i == 1664495672) {
                stsdData.requiredSampleTransformation = 1;
                str2 = "application/x-mp4-cea-608";
            } else {
                throw new IllegalStateException();
            }
        }
        stsdData.format = new Format.Builder().setId(i4).setSampleMimeType(str2).setLanguage(str).setSubsampleOffsetUs(j).setInitializationData(immutableList).build();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v30, types: [java.util.List<byte[]>] */
    /* JADX WARN: Type inference failed for: r6v28, types: [java.util.List<byte[]>] */
    private static void parseVideoSampleEntry(ParsableByteArray parsableByteArray, int i, int i2, int i3, int i4, int i5, DrmInitData drmInitData, StsdData stsdData, int i6) throws ParserException {
        String str;
        DrmInitData drmInitData2;
        int i7;
        int i8;
        int i9;
        float f;
        int i10;
        int i11;
        int i12;
        int i13 = i2;
        int i14 = i3;
        DrmInitData drmInitData3 = drmInitData;
        StsdData stsdData2 = stsdData;
        int i15 = 8;
        parsableByteArray.setPosition(i13 + 8 + 8);
        parsableByteArray.skipBytes(16);
        int readUnsignedShort = parsableByteArray.readUnsignedShort();
        int readUnsignedShort2 = parsableByteArray.readUnsignedShort();
        parsableByteArray.skipBytes(50);
        int position = parsableByteArray.getPosition();
        int i16 = i;
        if (i16 == 1701733238) {
            Pair<Integer, TrackEncryptionBox> parseSampleEntryEncryptionData = parseSampleEntryEncryptionData(parsableByteArray, i13, i14);
            if (parseSampleEntryEncryptionData != null) {
                i16 = ((Integer) parseSampleEntryEncryptionData.first).intValue();
                drmInitData3 = drmInitData3 == null ? null : drmInitData3.copyWithSchemeType(((TrackEncryptionBox) parseSampleEntryEncryptionData.second).schemeType);
                stsdData2.trackEncryptionBoxes[i6] = (TrackEncryptionBox) parseSampleEntryEncryptionData.second;
            }
            parsableByteArray.setPosition(position);
        }
        String str2 = "video/3gpp";
        String str3 = i16 == 1831958048 ? "video/mpeg" : i16 == 1211250227 ? "video/3gpp" : null;
        float f2 = 1.0f;
        ImmutableList immutableList = null;
        String str4 = null;
        byte[] bArr = null;
        int i17 = -1;
        int i18 = -1;
        int i19 = -1;
        int i20 = -1;
        ByteBuffer byteBuffer = null;
        EsdsData esdsData = null;
        boolean z = false;
        int i21 = position;
        int i22 = 8;
        while (i21 - i13 < i14) {
            parsableByteArray.setPosition(i21);
            int position2 = parsableByteArray.getPosition();
            int readInt = parsableByteArray.readInt();
            if (readInt == 0) {
                str = str2;
                if (parsableByteArray.getPosition() - i13 == i14) {
                    break;
                }
            } else {
                str = str2;
            }
            ExtractorUtil.checkContainerInput(readInt > 0, "childAtomSize must be positive");
            int readInt2 = parsableByteArray.readInt();
            if (readInt2 == 1635148611) {
                ExtractorUtil.checkContainerInput(str3 == null, null);
                parsableByteArray.setPosition(position2 + 8);
                AvcConfig parse = AvcConfig.parse(parsableByteArray);
                ?? r6 = parse.initializationData;
                stsdData2.nalUnitLengthFieldLength = parse.nalUnitLengthFieldLength;
                if (!z) {
                    f2 = parse.pixelWidthHeightRatio;
                }
                String str5 = parse.codecs;
                int i23 = parse.colorSpace;
                int i24 = parse.colorRange;
                int i25 = parse.colorTransfer;
                i12 = parse.bitdepthLuma;
                drmInitData2 = drmInitData3;
                str4 = str5;
                i9 = i16;
                i18 = i23;
                i19 = i24;
                i20 = i25;
                str3 = "video/avc";
                i22 = parse.bitdepthChroma;
                immutableList = r6;
            } else {
                if (readInt2 == 1752589123) {
                    ExtractorUtil.checkContainerInput(str3 == null, null);
                    parsableByteArray.setPosition(position2 + 8);
                    HevcConfig parse2 = HevcConfig.parse(parsableByteArray);
                    ?? r2 = parse2.initializationData;
                    stsdData2.nalUnitLengthFieldLength = parse2.nalUnitLengthFieldLength;
                    if (!z) {
                        f2 = parse2.pixelWidthHeightRatio;
                    }
                    String str6 = parse2.codecs;
                    int i26 = parse2.colorSpace;
                    drmInitData2 = drmInitData3;
                    str4 = str6;
                    i18 = i26;
                    i9 = i16;
                    i19 = parse2.colorRange;
                    i20 = parse2.colorTransfer;
                    i15 = parse2.bitdepthLuma;
                    str3 = "video/hevc";
                    i22 = parse2.bitdepthChroma;
                    immutableList = r2;
                } else {
                    if (readInt2 == 1685480259 || readInt2 == 1685485123) {
                        drmInitData2 = drmInitData3;
                        i7 = i15;
                        i8 = i22;
                        i9 = i16;
                        f = f2;
                        i10 = i18;
                        i11 = i20;
                        DolbyVisionConfig parse3 = DolbyVisionConfig.parse(parsableByteArray);
                        if (parse3 != null) {
                            str3 = "video/dolby-vision";
                            str4 = parse3.codecs;
                        }
                    } else {
                        if (readInt2 == 1987076931) {
                            ExtractorUtil.checkContainerInput(str3 == null, null);
                            String str7 = i16 == 1987063864 ? "video/x-vnd.on2.vp8" : "video/x-vnd.on2.vp9";
                            parsableByteArray.setPosition(position2 + 12);
                            parsableByteArray.skipBytes(2);
                            int readUnsignedByte = parsableByteArray.readUnsignedByte();
                            i15 = readUnsignedByte >> 4;
                            boolean z2 = (readUnsignedByte & 1) != 0;
                            int readUnsignedByte2 = parsableByteArray.readUnsignedByte();
                            int readUnsignedByte3 = parsableByteArray.readUnsignedByte();
                            i18 = ColorInfo.isoColorPrimariesToColorSpace(readUnsignedByte2);
                            i19 = z2 ? 1 : 2;
                            i20 = ColorInfo.isoTransferCharacteristicsToColorTransfer(readUnsignedByte3);
                            str3 = str7;
                            drmInitData2 = drmInitData3;
                            i22 = i15;
                        } else if (readInt2 == 1635135811) {
                            parsableByteArray.setPosition(position2 + 8);
                            ColorInfo parseAv1c = parseAv1c(parsableByteArray);
                            i12 = parseAv1c.lumaBitdepth;
                            int i27 = parseAv1c.chromaBitdepth;
                            int i28 = parseAv1c.colorSpace;
                            int i29 = parseAv1c.colorRange;
                            i20 = parseAv1c.colorTransfer;
                            drmInitData2 = drmInitData3;
                            i18 = i28;
                            i9 = i16;
                            i19 = i29;
                            str3 = "video/av01";
                            i22 = i27;
                        } else if (readInt2 == 1668050025) {
                            if (byteBuffer == null) {
                                byteBuffer = allocateHdrStaticInfo();
                            }
                            ByteBuffer byteBuffer2 = byteBuffer;
                            byteBuffer2.position(21);
                            byteBuffer2.putShort(parsableByteArray.readShort());
                            byteBuffer2.putShort(parsableByteArray.readShort());
                            byteBuffer = byteBuffer2;
                            drmInitData2 = drmInitData3;
                        } else if (readInt2 == 1835295606) {
                            if (byteBuffer == null) {
                                byteBuffer = allocateHdrStaticInfo();
                            }
                            ByteBuffer byteBuffer3 = byteBuffer;
                            short readShort = parsableByteArray.readShort();
                            short readShort2 = parsableByteArray.readShort();
                            short readShort3 = parsableByteArray.readShort();
                            i9 = i16;
                            short readShort4 = parsableByteArray.readShort();
                            short readShort5 = parsableByteArray.readShort();
                            int i30 = i22;
                            short readShort6 = parsableByteArray.readShort();
                            int i31 = i15;
                            short readShort7 = parsableByteArray.readShort();
                            drmInitData2 = drmInitData3;
                            short readShort8 = parsableByteArray.readShort();
                            long readUnsignedInt = parsableByteArray.readUnsignedInt();
                            long readUnsignedInt2 = parsableByteArray.readUnsignedInt();
                            byteBuffer3.position(1);
                            byteBuffer3.putShort(readShort5);
                            byteBuffer3.putShort(readShort6);
                            byteBuffer3.putShort(readShort);
                            byteBuffer3.putShort(readShort2);
                            byteBuffer3.putShort(readShort3);
                            byteBuffer3.putShort(readShort4);
                            byteBuffer3.putShort(readShort7);
                            byteBuffer3.putShort(readShort8);
                            byteBuffer3.putShort((short) (readUnsignedInt / 10000));
                            byteBuffer3.putShort((short) (readUnsignedInt2 / 10000));
                            byteBuffer = byteBuffer3;
                            i22 = i30;
                            i15 = i31;
                            f2 = f2;
                        } else {
                            drmInitData2 = drmInitData3;
                            i7 = i15;
                            i8 = i22;
                            i9 = i16;
                            f = f2;
                            if (readInt2 == 1681012275) {
                                ExtractorUtil.checkContainerInput(str3 == null, null);
                                str3 = str;
                            } else if (readInt2 == 1702061171) {
                                ExtractorUtil.checkContainerInput(str3 == null, null);
                                esdsData = parseEsdsFromParent(parsableByteArray, position2);
                                String str8 = esdsData.mimeType;
                                byte[] bArr2 = esdsData.initializationData;
                                if (bArr2 != null) {
                                    immutableList = ImmutableList.of(bArr2);
                                }
                                str3 = str8;
                            } else if (readInt2 == 1885434736) {
                                f2 = parsePaspFromParent(parsableByteArray, position2);
                                i22 = i8;
                                i15 = i7;
                                z = true;
                                i21 += readInt;
                                i13 = i2;
                                i14 = i3;
                                stsdData2 = stsdData;
                                str2 = str;
                                i16 = i9;
                                drmInitData3 = drmInitData2;
                            } else if (readInt2 == 1937126244) {
                                bArr = parseProjFromParent(parsableByteArray, position2, readInt);
                            } else if (readInt2 == 1936995172) {
                                int readUnsignedByte4 = parsableByteArray.readUnsignedByte();
                                parsableByteArray.skipBytes(3);
                                if (readUnsignedByte4 == 0) {
                                    int readUnsignedByte5 = parsableByteArray.readUnsignedByte();
                                    if (readUnsignedByte5 == 0) {
                                        i17 = 0;
                                    } else if (readUnsignedByte5 == 1) {
                                        i17 = 1;
                                    } else if (readUnsignedByte5 == 2) {
                                        i17 = 2;
                                    } else if (readUnsignedByte5 == 3) {
                                        i17 = 3;
                                    }
                                }
                            } else {
                                i10 = i18;
                                if (readInt2 == 1668246642) {
                                    i11 = i20;
                                    if (i10 == -1 && i11 == -1) {
                                        int readInt3 = parsableByteArray.readInt();
                                        if (readInt3 == TYPE_nclx || readInt3 == TYPE_nclc) {
                                            int readUnsignedShort3 = parsableByteArray.readUnsignedShort();
                                            int readUnsignedShort4 = parsableByteArray.readUnsignedShort();
                                            parsableByteArray.skipBytes(2);
                                            boolean z3 = readInt == 19 && (parsableByteArray.readUnsignedByte() & 128) != 0;
                                            i18 = ColorInfo.isoColorPrimariesToColorSpace(readUnsignedShort3);
                                            i19 = z3 ? 1 : 2;
                                            i20 = ColorInfo.isoTransferCharacteristicsToColorTransfer(readUnsignedShort4);
                                            i22 = i8;
                                            i15 = i7;
                                            f2 = f;
                                            i21 += readInt;
                                            i13 = i2;
                                            i14 = i3;
                                            stsdData2 = stsdData;
                                            str2 = str;
                                            i16 = i9;
                                            drmInitData3 = drmInitData2;
                                        } else {
                                            Log.w(TAG, "Unsupported color type: " + Atom.getAtomTypeString(readInt3));
                                        }
                                    }
                                } else {
                                    i11 = i20;
                                }
                            }
                            i22 = i8;
                            i15 = i7;
                            f2 = f;
                            i21 += readInt;
                            i13 = i2;
                            i14 = i3;
                            stsdData2 = stsdData;
                            str2 = str;
                            i16 = i9;
                            drmInitData3 = drmInitData2;
                        }
                        i9 = i16;
                    }
                    i20 = i11;
                    i18 = i10;
                    i22 = i8;
                    i15 = i7;
                    f2 = f;
                    i21 += readInt;
                    i13 = i2;
                    i14 = i3;
                    stsdData2 = stsdData;
                    str2 = str;
                    i16 = i9;
                    drmInitData3 = drmInitData2;
                }
                i21 += readInt;
                i13 = i2;
                i14 = i3;
                stsdData2 = stsdData;
                str2 = str;
                i16 = i9;
                drmInitData3 = drmInitData2;
            }
            i15 = i12;
            i21 += readInt;
            i13 = i2;
            i14 = i3;
            stsdData2 = stsdData;
            str2 = str;
            i16 = i9;
            drmInitData3 = drmInitData2;
        }
        DrmInitData drmInitData4 = drmInitData3;
        int i32 = i15;
        int i33 = i22;
        float f3 = f2;
        int i34 = i18;
        int i35 = i20;
        if (str3 == null) {
            return;
        }
        Format.Builder colorInfo = new Format.Builder().setId(i4).setSampleMimeType(str3).setCodecs(str4).setWidth(readUnsignedShort).setHeight(readUnsignedShort2).setPixelWidthHeightRatio(f3).setRotationDegrees(i5).setProjectionData(bArr).setStereoMode(i17).setInitializationData(immutableList).setDrmInitData(drmInitData4).setColorInfo(new ColorInfo.Builder().setColorSpace(i34).setColorRange(i19).setColorTransfer(i35).setHdrStaticInfo(byteBuffer != null ? byteBuffer.array() : null).setLumaBitdepth(i32).setChromaBitdepth(i33).build());
        if (esdsData != null) {
            colorInfo.setAverageBitrate(Ints.saturatedCast(esdsData.bitrate)).setPeakBitrate(Ints.saturatedCast(esdsData.peakBitrate));
        }
        stsdData.format = colorInfo.build();
    }

    private static ColorInfo parseAv1c(ParsableByteArray parsableByteArray) {
        ColorInfo.Builder builder = new ColorInfo.Builder();
        ParsableBitArray parsableBitArray = new ParsableBitArray(parsableByteArray.getData());
        parsableBitArray.setPosition(parsableByteArray.getPosition() * 8);
        parsableBitArray.skipBytes(1);
        int readBits = parsableBitArray.readBits(3);
        parsableBitArray.skipBits(6);
        boolean readBit = parsableBitArray.readBit();
        boolean readBit2 = parsableBitArray.readBit();
        if (readBits == 2 && readBit) {
            builder.setLumaBitdepth(readBit2 ? 12 : 10);
            builder.setChromaBitdepth(readBit2 ? 12 : 10);
        } else if (readBits <= 2) {
            builder.setLumaBitdepth(readBit ? 10 : 8);
            builder.setChromaBitdepth(readBit ? 10 : 8);
        }
        parsableBitArray.skipBits(13);
        parsableBitArray.skipBit();
        int readBits2 = parsableBitArray.readBits(4);
        if (readBits2 != 1) {
            Log.i(TAG, "Unsupported obu_type: " + readBits2);
            return builder.build();
        } else if (parsableBitArray.readBit()) {
            Log.i(TAG, "Unsupported obu_extension_flag");
            return builder.build();
        } else {
            boolean readBit3 = parsableBitArray.readBit();
            parsableBitArray.skipBit();
            if (readBit3 && parsableBitArray.readBits(8) > 127) {
                Log.i(TAG, "Excessive obu_size");
                return builder.build();
            }
            int readBits3 = parsableBitArray.readBits(3);
            parsableBitArray.skipBit();
            if (parsableBitArray.readBit()) {
                Log.i(TAG, "Unsupported reduced_still_picture_header");
                return builder.build();
            } else if (parsableBitArray.readBit()) {
                Log.i(TAG, "Unsupported timing_info_present_flag");
                return builder.build();
            } else if (parsableBitArray.readBit()) {
                Log.i(TAG, "Unsupported initial_display_delay_present_flag");
                return builder.build();
            } else {
                int readBits4 = parsableBitArray.readBits(5);
                boolean z = false;
                for (int i = 0; i <= readBits4; i++) {
                    parsableBitArray.skipBits(12);
                    if (parsableBitArray.readBits(5) > 7) {
                        parsableBitArray.skipBit();
                    }
                }
                int readBits5 = parsableBitArray.readBits(4);
                int readBits6 = parsableBitArray.readBits(4);
                parsableBitArray.skipBits(readBits5 + 1);
                parsableBitArray.skipBits(readBits6 + 1);
                if (parsableBitArray.readBit()) {
                    parsableBitArray.skipBits(7);
                }
                parsableBitArray.skipBits(7);
                boolean readBit4 = parsableBitArray.readBit();
                if (readBit4) {
                    parsableBitArray.skipBits(2);
                }
                if ((parsableBitArray.readBit() ? 2 : parsableBitArray.readBits(1)) > 0 && !parsableBitArray.readBit()) {
                    parsableBitArray.skipBits(1);
                }
                if (readBit4) {
                    parsableBitArray.skipBits(3);
                }
                parsableBitArray.skipBits(3);
                boolean readBit5 = parsableBitArray.readBit();
                if (readBits3 == 2 && readBit5) {
                    parsableBitArray.skipBit();
                }
                if (readBits3 != 1 && parsableBitArray.readBit()) {
                    z = true;
                }
                if (parsableBitArray.readBit()) {
                    int readBits7 = parsableBitArray.readBits(8);
                    int readBits8 = parsableBitArray.readBits(8);
                    builder.setColorSpace(ColorInfo.isoColorPrimariesToColorSpace(readBits7)).setColorRange(((z || readBits7 != 1 || readBits8 != 13 || parsableBitArray.readBits(8) != 0) ? parsableBitArray.readBits(1) : 1) != 1 ? 2 : 1).setColorTransfer(ColorInfo.isoTransferCharacteristicsToColorTransfer(readBits8));
                }
                return builder.build();
            }
        }
    }

    private static ByteBuffer allocateHdrStaticInfo() {
        return ByteBuffer.allocate(25).order(ByteOrder.LITTLE_ENDIAN);
    }

    private static void parseMetaDataSampleEntry(ParsableByteArray parsableByteArray, int i, int i2, int i3, StsdData stsdData) {
        parsableByteArray.setPosition(i2 + 8 + 8);
        if (i == 1835365492) {
            parsableByteArray.readNullTerminatedString();
            String readNullTerminatedString = parsableByteArray.readNullTerminatedString();
            if (readNullTerminatedString != null) {
                stsdData.format = new Format.Builder().setId(i3).setSampleMimeType(readNullTerminatedString).build();
            }
        }
    }

    private static Pair<long[], long[]> parseEdts(Atom.ContainerAtom containerAtom) {
        Atom.LeafAtom leafAtomOfType = containerAtom.getLeafAtomOfType(1701606260);
        if (leafAtomOfType == null) {
            return null;
        }
        ParsableByteArray parsableByteArray = leafAtomOfType.data;
        parsableByteArray.setPosition(8);
        int parseFullAtomVersion = Atom.parseFullAtomVersion(parsableByteArray.readInt());
        int readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
        long[] jArr = new long[readUnsignedIntToInt];
        long[] jArr2 = new long[readUnsignedIntToInt];
        for (int i = 0; i < readUnsignedIntToInt; i++) {
            jArr[i] = parseFullAtomVersion == 1 ? parsableByteArray.readUnsignedLongToLong() : parsableByteArray.readUnsignedInt();
            jArr2[i] = parseFullAtomVersion == 1 ? parsableByteArray.readLong() : parsableByteArray.readInt();
            if (parsableByteArray.readShort() != 1) {
                throw new IllegalArgumentException("Unsupported media rate.");
            }
            parsableByteArray.skipBytes(2);
        }
        return Pair.create(jArr, jArr2);
    }

    private static float parsePaspFromParent(ParsableByteArray parsableByteArray, int i) {
        parsableByteArray.setPosition(i + 8);
        return parsableByteArray.readUnsignedIntToInt() / parsableByteArray.readUnsignedIntToInt();
    }

    private static void parseAudioSampleEntry(ParsableByteArray parsableByteArray, int i, int i2, int i3, int i4, String str, boolean z, DrmInitData drmInitData, StsdData stsdData, int i5) throws ParserException {
        int i6;
        int readUnsignedShort;
        int readUnsignedFixedPoint1616;
        int readInt;
        int i7;
        String str2;
        String str3;
        int i8;
        int i9 = i2;
        int i10 = i3;
        DrmInitData drmInitData2 = drmInitData;
        parsableByteArray.setPosition(i9 + 8 + 8);
        if (z) {
            i6 = parsableByteArray.readUnsignedShort();
            parsableByteArray.skipBytes(6);
        } else {
            parsableByteArray.skipBytes(8);
            i6 = 0;
        }
        if (i6 == 0 || i6 == 1) {
            readUnsignedShort = parsableByteArray.readUnsignedShort();
            parsableByteArray.skipBytes(6);
            readUnsignedFixedPoint1616 = parsableByteArray.readUnsignedFixedPoint1616();
            parsableByteArray.setPosition(parsableByteArray.getPosition() - 4);
            readInt = parsableByteArray.readInt();
            if (i6 == 1) {
                parsableByteArray.skipBytes(16);
            }
            i7 = -1;
        } else if (i6 != 2) {
            return;
        } else {
            parsableByteArray.skipBytes(16);
            readUnsignedFixedPoint1616 = (int) Math.round(parsableByteArray.readDouble());
            readUnsignedShort = parsableByteArray.readUnsignedIntToInt();
            parsableByteArray.skipBytes(4);
            int readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
            int readUnsignedIntToInt2 = parsableByteArray.readUnsignedIntToInt();
            boolean z2 = (readUnsignedIntToInt2 & 1) != 0;
            boolean z3 = (readUnsignedIntToInt2 & 2) != 0;
            if (!z2) {
                if (readUnsignedIntToInt == 8) {
                    i7 = 3;
                } else if (readUnsignedIntToInt == 16) {
                    i7 = z3 ? 268435456 : 2;
                } else if (readUnsignedIntToInt == 24) {
                    i7 = z3 ? C.ENCODING_PCM_24BIT_BIG_ENDIAN : 21;
                } else {
                    if (readUnsignedIntToInt == 32) {
                        i7 = z3 ? C.ENCODING_PCM_32BIT_BIG_ENDIAN : 22;
                    }
                    i7 = -1;
                }
                parsableByteArray.skipBytes(8);
                readInt = 0;
            } else {
                if (readUnsignedIntToInt == 32) {
                    i7 = 4;
                    parsableByteArray.skipBytes(8);
                    readInt = 0;
                }
                i7 = -1;
                parsableByteArray.skipBytes(8);
                readInt = 0;
            }
        }
        int position = parsableByteArray.getPosition();
        int i11 = i;
        if (i11 == 1701733217) {
            Pair<Integer, TrackEncryptionBox> parseSampleEntryEncryptionData = parseSampleEntryEncryptionData(parsableByteArray, i9, i10);
            if (parseSampleEntryEncryptionData != null) {
                i11 = ((Integer) parseSampleEntryEncryptionData.first).intValue();
                drmInitData2 = drmInitData2 == null ? null : drmInitData2.copyWithSchemeType(((TrackEncryptionBox) parseSampleEntryEncryptionData.second).schemeType);
                stsdData.trackEncryptionBoxes[i5] = (TrackEncryptionBox) parseSampleEntryEncryptionData.second;
            }
            parsableByteArray.setPosition(position);
        }
        String str4 = MimeTypes.AUDIO_MPEGH_MHM1;
        if (i11 == 1633889587) {
            str2 = "audio/ac3";
        } else if (i11 == 1700998451) {
            str2 = "audio/eac3";
        } else if (i11 == 1633889588) {
            str2 = "audio/ac4";
        } else if (i11 == 1685353315) {
            str2 = "audio/vnd.dts";
        } else if (i11 == 1685353320 || i11 == 1685353324) {
            str2 = "audio/vnd.dts.hd";
        } else if (i11 == 1685353317) {
            str2 = "audio/vnd.dts.hd;profile=lbr";
        } else if (i11 == 1685353336) {
            str2 = MimeTypes.AUDIO_DTS_X;
        } else if (i11 == 1935764850) {
            str2 = "audio/3gpp";
        } else if (i11 == 1935767394) {
            str2 = "audio/amr-wb";
        } else {
            if (i11 != 1936684916) {
                if (i11 == 1953984371) {
                    str2 = "audio/raw";
                    i7 = 268435456;
                } else if (i11 != 1819304813) {
                    str2 = (i11 == 778924082 || i11 == 778924083) ? "audio/mpeg" : i11 == 1835557169 ? MimeTypes.AUDIO_MPEGH_MHA1 : i11 == 1835560241 ? MimeTypes.AUDIO_MPEGH_MHM1 : i11 == 1634492771 ? "audio/alac" : i11 == 1634492791 ? "audio/g711-alaw" : i11 == 1970037111 ? "audio/g711-mlaw" : i11 == 1332770163 ? "audio/opus" : i11 == 1716281667 ? "audio/flac" : i11 == 1835823201 ? "audio/true-hd" : null;
                } else if (i7 != -1) {
                    str2 = "audio/raw";
                }
            }
            str2 = "audio/raw";
            i7 = 2;
        }
        int i12 = i7;
        List<byte[]> list = null;
        String str5 = null;
        EsdsData esdsData = null;
        while (position - i9 < i10) {
            parsableByteArray.setPosition(position);
            int readInt2 = parsableByteArray.readInt();
            ExtractorUtil.checkContainerInput(readInt2 > 0, "childAtomSize must be positive");
            int readInt3 = parsableByteArray.readInt();
            if (readInt3 == 1835557187) {
                parsableByteArray.setPosition(position + 8);
                parsableByteArray.skipBytes(1);
                int readUnsignedByte = parsableByteArray.readUnsignedByte();
                parsableByteArray.skipBytes(1);
                if (Objects.equals(str2, str4)) {
                    i8 = 0;
                    str5 = String.format("mhm1.%02X", Integer.valueOf(readUnsignedByte));
                    str3 = str4;
                } else {
                    str3 = str4;
                    i8 = 0;
                    str5 = String.format("mha1.%02X", Integer.valueOf(readUnsignedByte));
                }
                int readUnsignedShort2 = parsableByteArray.readUnsignedShort();
                byte[] bArr = new byte[readUnsignedShort2];
                parsableByteArray.readBytes(bArr, i8, readUnsignedShort2);
                if (list == null) {
                    list = ImmutableList.of(bArr);
                } else {
                    list = ImmutableList.of(bArr, list.get(i8));
                }
            } else {
                str3 = str4;
                if (readInt3 == 1835557200) {
                    parsableByteArray.setPosition(position + 8);
                    int readUnsignedByte2 = parsableByteArray.readUnsignedByte();
                    if (readUnsignedByte2 > 0) {
                        byte[] bArr2 = new byte[readUnsignedByte2];
                        parsableByteArray.readBytes(bArr2, 0, readUnsignedByte2);
                        if (list == null) {
                            list = ImmutableList.of(bArr2);
                        } else {
                            list = ImmutableList.of(list.get(0), bArr2);
                        }
                    }
                } else {
                    if (readInt3 == 1702061171 || (z && readInt3 == 2002876005)) {
                        int findBoxPosition = readInt3 == 1702061171 ? position : findBoxPosition(parsableByteArray, 1702061171, position, readInt2);
                        if (findBoxPosition != -1) {
                            esdsData = parseEsdsFromParent(parsableByteArray, findBoxPosition);
                            str2 = esdsData.mimeType;
                            byte[] bArr3 = esdsData.initializationData;
                            if (bArr3 != null) {
                                if ("audio/vorbis".equals(str2)) {
                                    list = VorbisUtil.parseVorbisCsdFromEsdsInitializationData(bArr3);
                                } else {
                                    if ("audio/mp4a-latm".equals(str2)) {
                                        AacUtil.Config parseAudioSpecificConfig = AacUtil.parseAudioSpecificConfig(bArr3);
                                        int i13 = parseAudioSpecificConfig.sampleRateHz;
                                        int i14 = parseAudioSpecificConfig.channelCount;
                                        str5 = parseAudioSpecificConfig.codecs;
                                        readUnsignedFixedPoint1616 = i13;
                                        readUnsignedShort = i14;
                                    }
                                    list = ImmutableList.of(bArr3);
                                }
                            }
                        }
                    } else if (readInt3 == 1684103987) {
                        parsableByteArray.setPosition(position + 8);
                        stsdData.format = Ac3Util.parseAc3AnnexFFormat(parsableByteArray, Integer.toString(i4), str, drmInitData2);
                    } else if (readInt3 == 1684366131) {
                        parsableByteArray.setPosition(position + 8);
                        stsdData.format = Ac3Util.parseEAc3AnnexFFormat(parsableByteArray, Integer.toString(i4), str, drmInitData2);
                    } else if (readInt3 == 1684103988) {
                        parsableByteArray.setPosition(position + 8);
                        stsdData.format = Ac4Util.parseAc4AnnexEFormat(parsableByteArray, Integer.toString(i4), str, drmInitData2);
                    } else if (readInt3 == 1684892784) {
                        if (readInt <= 0) {
                            throw ParserException.createForMalformedContainer("Invalid sample rate for Dolby TrueHD MLP stream: " + readInt, null);
                        }
                        readUnsignedFixedPoint1616 = readInt;
                        readUnsignedShort = 2;
                    } else if (readInt3 == 1684305011 || readInt3 == 1969517683) {
                        stsdData.format = new Format.Builder().setId(i4).setSampleMimeType(str2).setChannelCount(readUnsignedShort).setSampleRate(readUnsignedFixedPoint1616).setDrmInitData(drmInitData2).setLanguage(str).build();
                    } else if (readInt3 == 1682927731) {
                        int i15 = readInt2 - 8;
                        byte[] bArr4 = opusMagic;
                        byte[] copyOf = Arrays.copyOf(bArr4, bArr4.length + i15);
                        parsableByteArray.setPosition(position + 8);
                        parsableByteArray.readBytes(copyOf, bArr4.length, i15);
                        list = OpusUtil.buildInitializationData(copyOf);
                    } else if (readInt3 == 1684425825) {
                        int i16 = readInt2 - 12;
                        byte[] bArr5 = new byte[i16 + 4];
                        bArr5[0] = 102;
                        bArr5[1] = 76;
                        bArr5[2] = 97;
                        bArr5[3] = 67;
                        parsableByteArray.setPosition(position + 12);
                        parsableByteArray.readBytes(bArr5, 4, i16);
                        list = ImmutableList.of(bArr5);
                    } else if (readInt3 == 1634492771) {
                        int i17 = readInt2 - 12;
                        byte[] bArr6 = new byte[i17];
                        parsableByteArray.setPosition(position + 12);
                        parsableByteArray.readBytes(bArr6, 0, i17);
                        Pair<Integer, Integer> parseAlacAudioSpecificConfig = CodecSpecificDataUtil.parseAlacAudioSpecificConfig(bArr6);
                        int intValue = ((Integer) parseAlacAudioSpecificConfig.first).intValue();
                        readUnsignedShort = ((Integer) parseAlacAudioSpecificConfig.second).intValue();
                        list = ImmutableList.of(bArr6);
                        readUnsignedFixedPoint1616 = intValue;
                    }
                    position += readInt2;
                    i9 = i2;
                    i10 = i3;
                    str4 = str3;
                }
            }
            position += readInt2;
            i9 = i2;
            i10 = i3;
            str4 = str3;
        }
        if (stsdData.format != null || str2 == null) {
            return;
        }
        Format.Builder language = new Format.Builder().setId(i4).setSampleMimeType(str2).setCodecs(str5).setChannelCount(readUnsignedShort).setSampleRate(readUnsignedFixedPoint1616).setPcmEncoding(i12).setInitializationData(list).setDrmInitData(drmInitData2).setLanguage(str);
        if (esdsData != null) {
            language.setAverageBitrate(Ints.saturatedCast(esdsData.bitrate)).setPeakBitrate(Ints.saturatedCast(esdsData.peakBitrate));
        }
        stsdData.format = language.build();
    }

    private static int findBoxPosition(ParsableByteArray parsableByteArray, int i, int i2, int i3) throws ParserException {
        int position = parsableByteArray.getPosition();
        ExtractorUtil.checkContainerInput(position >= i2, null);
        while (position - i2 < i3) {
            parsableByteArray.setPosition(position);
            int readInt = parsableByteArray.readInt();
            ExtractorUtil.checkContainerInput(readInt > 0, "childAtomSize must be positive");
            if (parsableByteArray.readInt() == i) {
                return position;
            }
            position += readInt;
        }
        return -1;
    }

    private static EsdsData parseEsdsFromParent(ParsableByteArray parsableByteArray, int i) {
        parsableByteArray.setPosition(i + 8 + 4);
        parsableByteArray.skipBytes(1);
        parseExpandableClassSize(parsableByteArray);
        parsableByteArray.skipBytes(2);
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        if ((readUnsignedByte & 128) != 0) {
            parsableByteArray.skipBytes(2);
        }
        if ((readUnsignedByte & 64) != 0) {
            parsableByteArray.skipBytes(parsableByteArray.readUnsignedByte());
        }
        if ((readUnsignedByte & 32) != 0) {
            parsableByteArray.skipBytes(2);
        }
        parsableByteArray.skipBytes(1);
        parseExpandableClassSize(parsableByteArray);
        String mimeTypeFromMp4ObjectType = MimeTypes.getMimeTypeFromMp4ObjectType(parsableByteArray.readUnsignedByte());
        if ("audio/mpeg".equals(mimeTypeFromMp4ObjectType) || "audio/vnd.dts".equals(mimeTypeFromMp4ObjectType) || "audio/vnd.dts.hd".equals(mimeTypeFromMp4ObjectType)) {
            return new EsdsData(mimeTypeFromMp4ObjectType, null, -1L, -1L);
        }
        parsableByteArray.skipBytes(4);
        long readUnsignedInt = parsableByteArray.readUnsignedInt();
        long readUnsignedInt2 = parsableByteArray.readUnsignedInt();
        parsableByteArray.skipBytes(1);
        int parseExpandableClassSize = parseExpandableClassSize(parsableByteArray);
        byte[] bArr = new byte[parseExpandableClassSize];
        parsableByteArray.readBytes(bArr, 0, parseExpandableClassSize);
        return new EsdsData(mimeTypeFromMp4ObjectType, bArr, readUnsignedInt2 > 0 ? readUnsignedInt2 : -1L, readUnsignedInt > 0 ? readUnsignedInt : -1L);
    }

    private static Pair<Integer, TrackEncryptionBox> parseSampleEntryEncryptionData(ParsableByteArray parsableByteArray, int i, int i2) throws ParserException {
        Pair<Integer, TrackEncryptionBox> parseCommonEncryptionSinfFromParent;
        int position = parsableByteArray.getPosition();
        while (position - i < i2) {
            parsableByteArray.setPosition(position);
            int readInt = parsableByteArray.readInt();
            ExtractorUtil.checkContainerInput(readInt > 0, "childAtomSize must be positive");
            if (parsableByteArray.readInt() == 1936289382 && (parseCommonEncryptionSinfFromParent = parseCommonEncryptionSinfFromParent(parsableByteArray, position, readInt)) != null) {
                return parseCommonEncryptionSinfFromParent;
            }
            position += readInt;
        }
        return null;
    }

    static Pair<Integer, TrackEncryptionBox> parseCommonEncryptionSinfFromParent(ParsableByteArray parsableByteArray, int i, int i2) throws ParserException {
        int i3 = i + 8;
        String str = null;
        Integer num = null;
        int i4 = -1;
        int i5 = 0;
        while (i3 - i < i2) {
            parsableByteArray.setPosition(i3);
            int readInt = parsableByteArray.readInt();
            int readInt2 = parsableByteArray.readInt();
            if (readInt2 == 1718775137) {
                num = Integer.valueOf(parsableByteArray.readInt());
            } else if (readInt2 == 1935894637) {
                parsableByteArray.skipBytes(4);
                str = parsableByteArray.readString(4);
            } else if (readInt2 == 1935894633) {
                i4 = i3;
                i5 = readInt;
            }
            i3 += readInt;
        }
        if ("cenc".equals(str) || "cbc1".equals(str) || "cens".equals(str) || "cbcs".equals(str)) {
            ExtractorUtil.checkContainerInput(num != null, "frma atom is mandatory");
            ExtractorUtil.checkContainerInput(i4 != -1, "schi atom is mandatory");
            TrackEncryptionBox parseSchiFromParent = parseSchiFromParent(parsableByteArray, i4, i5, str);
            ExtractorUtil.checkContainerInput(parseSchiFromParent != null, "tenc atom is mandatory");
            return Pair.create(num, (TrackEncryptionBox) Util.castNonNull(parseSchiFromParent));
        }
        return null;
    }

    private static TrackEncryptionBox parseSchiFromParent(ParsableByteArray parsableByteArray, int i, int i2, String str) {
        int i3;
        int i4;
        int i5 = i + 8;
        while (true) {
            byte[] bArr = null;
            if (i5 - i >= i2) {
                return null;
            }
            parsableByteArray.setPosition(i5);
            int readInt = parsableByteArray.readInt();
            if (parsableByteArray.readInt() == 1952804451) {
                int parseFullAtomVersion = Atom.parseFullAtomVersion(parsableByteArray.readInt());
                parsableByteArray.skipBytes(1);
                if (parseFullAtomVersion == 0) {
                    parsableByteArray.skipBytes(1);
                    i4 = 0;
                    i3 = 0;
                } else {
                    int readUnsignedByte = parsableByteArray.readUnsignedByte();
                    i3 = readUnsignedByte & 15;
                    i4 = (readUnsignedByte & 240) >> 4;
                }
                boolean z = parsableByteArray.readUnsignedByte() == 1;
                int readUnsignedByte2 = parsableByteArray.readUnsignedByte();
                byte[] bArr2 = new byte[16];
                parsableByteArray.readBytes(bArr2, 0, 16);
                if (z && readUnsignedByte2 == 0) {
                    int readUnsignedByte3 = parsableByteArray.readUnsignedByte();
                    bArr = new byte[readUnsignedByte3];
                    parsableByteArray.readBytes(bArr, 0, readUnsignedByte3);
                }
                return new TrackEncryptionBox(z, str, readUnsignedByte2, bArr2, i4, i3, bArr);
            }
            i5 += readInt;
        }
    }

    private static byte[] parseProjFromParent(ParsableByteArray parsableByteArray, int i, int i2) {
        int i3 = i + 8;
        while (i3 - i < i2) {
            parsableByteArray.setPosition(i3);
            int readInt = parsableByteArray.readInt();
            if (parsableByteArray.readInt() == 1886547818) {
                return Arrays.copyOfRange(parsableByteArray.getData(), i3, readInt + i3);
            }
            i3 += readInt;
        }
        return null;
    }

    private static int parseExpandableClassSize(ParsableByteArray parsableByteArray) {
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        int i = readUnsignedByte & WorkQueueKt.MASK;
        while ((readUnsignedByte & 128) == 128) {
            readUnsignedByte = parsableByteArray.readUnsignedByte();
            i = (i << 7) | (readUnsignedByte & WorkQueueKt.MASK);
        }
        return i;
    }

    private static boolean canApplyEditWithGaplessInfo(long[] jArr, long j, long j2, long j3) {
        int length = jArr.length - 1;
        return jArr[0] <= j2 && j2 < jArr[Util.constrainValue(4, 0, length)] && jArr[Util.constrainValue(jArr.length - 4, 0, length)] < j3 && j3 <= j;
    }

    private AtomParsers() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class ChunkIterator {
        private final ParsableByteArray chunkOffsets;
        private final boolean chunkOffsetsAreLongs;
        public int index;
        public final int length;
        private int nextSamplesPerChunkChangeIndex;
        public int numSamples;
        public long offset;
        private int remainingSamplesPerChunkChanges;
        private final ParsableByteArray stsc;

        public ChunkIterator(ParsableByteArray parsableByteArray, ParsableByteArray parsableByteArray2, boolean z) throws ParserException {
            this.stsc = parsableByteArray;
            this.chunkOffsets = parsableByteArray2;
            this.chunkOffsetsAreLongs = z;
            parsableByteArray2.setPosition(12);
            this.length = parsableByteArray2.readUnsignedIntToInt();
            parsableByteArray.setPosition(12);
            this.remainingSamplesPerChunkChanges = parsableByteArray.readUnsignedIntToInt();
            ExtractorUtil.checkContainerInput(parsableByteArray.readInt() == 1, "first_chunk must be 1");
            this.index = -1;
        }

        public boolean moveNext() {
            long readUnsignedInt;
            int i = this.index + 1;
            this.index = i;
            if (i == this.length) {
                return false;
            }
            if (this.chunkOffsetsAreLongs) {
                readUnsignedInt = this.chunkOffsets.readUnsignedLongToLong();
            } else {
                readUnsignedInt = this.chunkOffsets.readUnsignedInt();
            }
            this.offset = readUnsignedInt;
            if (this.index == this.nextSamplesPerChunkChangeIndex) {
                this.numSamples = this.stsc.readUnsignedIntToInt();
                this.stsc.skipBytes(4);
                int i2 = this.remainingSamplesPerChunkChanges - 1;
                this.remainingSamplesPerChunkChanges = i2;
                this.nextSamplesPerChunkChangeIndex = i2 > 0 ? this.stsc.readUnsignedIntToInt() - 1 : -1;
            }
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class TkhdData {
        private final long duration;
        private final int id;
        private final int rotationDegrees;

        public TkhdData(int i, long j, int i2) {
            this.id = i;
            this.duration = j;
            this.rotationDegrees = i2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class StsdData {
        public static final int STSD_HEADER_SIZE = 8;
        public Format format;
        public int nalUnitLengthFieldLength;
        public int requiredSampleTransformation = 0;
        public final TrackEncryptionBox[] trackEncryptionBoxes;

        public StsdData(int i) {
            this.trackEncryptionBoxes = new TrackEncryptionBox[i];
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class EsdsData {
        private final long bitrate;
        private final byte[] initializationData;
        private final String mimeType;
        private final long peakBitrate;

        public EsdsData(String str, byte[] bArr, long j, long j2) {
            this.mimeType = str;
            this.initializationData = bArr;
            this.bitrate = j;
            this.peakBitrate = j2;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class StszSampleSizeBox implements SampleSizeBox {
        private final ParsableByteArray data;
        private final int fixedSampleSize;
        private final int sampleCount;

        public StszSampleSizeBox(Atom.LeafAtom leafAtom, Format format) {
            ParsableByteArray parsableByteArray = leafAtom.data;
            this.data = parsableByteArray;
            parsableByteArray.setPosition(12);
            int readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
            if ("audio/raw".equals(format.sampleMimeType)) {
                int pcmFrameSize = Util.getPcmFrameSize(format.pcmEncoding, format.channelCount);
                if (readUnsignedIntToInt == 0 || readUnsignedIntToInt % pcmFrameSize != 0) {
                    Log.w(AtomParsers.TAG, "Audio sample size mismatch. stsd sample size: " + pcmFrameSize + ", stsz sample size: " + readUnsignedIntToInt);
                    readUnsignedIntToInt = pcmFrameSize;
                }
            }
            this.fixedSampleSize = readUnsignedIntToInt == 0 ? -1 : readUnsignedIntToInt;
            this.sampleCount = parsableByteArray.readUnsignedIntToInt();
        }

        @Override // androidx.media3.extractor.mp4.AtomParsers.SampleSizeBox
        public int getSampleCount() {
            return this.sampleCount;
        }

        @Override // androidx.media3.extractor.mp4.AtomParsers.SampleSizeBox
        public int getFixedSampleSize() {
            return this.fixedSampleSize;
        }

        @Override // androidx.media3.extractor.mp4.AtomParsers.SampleSizeBox
        public int readNextSampleSize() {
            int i = this.fixedSampleSize;
            return i == -1 ? this.data.readUnsignedIntToInt() : i;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static final class Stz2SampleSizeBox implements SampleSizeBox {
        private int currentByte;
        private final ParsableByteArray data;
        private final int fieldSize;
        private final int sampleCount;
        private int sampleIndex;

        @Override // androidx.media3.extractor.mp4.AtomParsers.SampleSizeBox
        public int getFixedSampleSize() {
            return -1;
        }

        public Stz2SampleSizeBox(Atom.LeafAtom leafAtom) {
            ParsableByteArray parsableByteArray = leafAtom.data;
            this.data = parsableByteArray;
            parsableByteArray.setPosition(12);
            this.fieldSize = parsableByteArray.readUnsignedIntToInt() & 255;
            this.sampleCount = parsableByteArray.readUnsignedIntToInt();
        }

        @Override // androidx.media3.extractor.mp4.AtomParsers.SampleSizeBox
        public int getSampleCount() {
            return this.sampleCount;
        }

        @Override // androidx.media3.extractor.mp4.AtomParsers.SampleSizeBox
        public int readNextSampleSize() {
            int i = this.fieldSize;
            if (i == 8) {
                return this.data.readUnsignedByte();
            }
            if (i == 16) {
                return this.data.readUnsignedShort();
            }
            int i2 = this.sampleIndex;
            this.sampleIndex = i2 + 1;
            if (i2 % 2 == 0) {
                int readUnsignedByte = this.data.readUnsignedByte();
                this.currentByte = readUnsignedByte;
                return (readUnsignedByte & 240) >> 4;
            }
            return this.currentByte & 15;
        }
    }
}

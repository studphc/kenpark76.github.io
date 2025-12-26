package androidx.media3.extractor.mp4;

import androidx.media3.common.util.Log;
import androidx.media3.common.util.ParsableByteArray;
import java.nio.ByteBuffer;
import java.util.UUID;
/* loaded from: classes.dex */
public final class PsshAtomUtil {
    private static final String TAG = "PsshAtomUtil";

    private PsshAtomUtil() {
    }

    public static byte[] buildPsshAtom(UUID uuid, byte[] bArr) {
        return buildPsshAtom(uuid, null, bArr);
    }

    public static byte[] buildPsshAtom(UUID uuid, UUID[] uuidArr, byte[] bArr) {
        int length = (bArr != null ? bArr.length : 0) + 32;
        if (uuidArr != null) {
            length += (uuidArr.length * 16) + 4;
        }
        ByteBuffer allocate = ByteBuffer.allocate(length);
        allocate.putInt(length);
        allocate.putInt(1886614376);
        allocate.putInt(uuidArr != null ? 16777216 : 0);
        allocate.putLong(uuid.getMostSignificantBits());
        allocate.putLong(uuid.getLeastSignificantBits());
        if (uuidArr != null) {
            allocate.putInt(uuidArr.length);
            for (UUID uuid2 : uuidArr) {
                allocate.putLong(uuid2.getMostSignificantBits());
                allocate.putLong(uuid2.getLeastSignificantBits());
            }
        }
        if (bArr != null && bArr.length != 0) {
            allocate.putInt(bArr.length);
            allocate.put(bArr);
        } else {
            allocate.putInt(0);
        }
        return allocate.array();
    }

    public static boolean isPsshAtom(byte[] bArr) {
        return parsePsshAtom(bArr) != null;
    }

    public static UUID parseUuid(byte[] bArr) {
        PsshAtom parsePsshAtom = parsePsshAtom(bArr);
        if (parsePsshAtom == null) {
            return null;
        }
        return parsePsshAtom.uuid;
    }

    public static int parseVersion(byte[] bArr) {
        PsshAtom parsePsshAtom = parsePsshAtom(bArr);
        if (parsePsshAtom == null) {
            return -1;
        }
        return parsePsshAtom.version;
    }

    public static byte[] parseSchemeSpecificData(byte[] bArr, UUID uuid) {
        PsshAtom parsePsshAtom = parsePsshAtom(bArr);
        if (parsePsshAtom == null) {
            return null;
        }
        if (!uuid.equals(parsePsshAtom.uuid)) {
            Log.w(TAG, "UUID mismatch. Expected: " + uuid + ", got: " + parsePsshAtom.uuid + ".");
            return null;
        }
        return parsePsshAtom.schemeData;
    }

    public static PsshAtom parsePsshAtom(byte[] bArr) {
        UUID[] uuidArr;
        ParsableByteArray parsableByteArray = new ParsableByteArray(bArr);
        if (parsableByteArray.limit() < 32) {
            return null;
        }
        parsableByteArray.setPosition(0);
        int bytesLeft = parsableByteArray.bytesLeft();
        int readInt = parsableByteArray.readInt();
        if (readInt != bytesLeft) {
            Log.w(TAG, "Advertised atom size (" + readInt + ") does not match buffer size: " + bytesLeft);
            return null;
        }
        int readInt2 = parsableByteArray.readInt();
        if (readInt2 != 1886614376) {
            Log.w(TAG, "Atom type is not pssh: " + readInt2);
            return null;
        }
        int parseFullAtomVersion = Atom.parseFullAtomVersion(parsableByteArray.readInt());
        if (parseFullAtomVersion > 1) {
            Log.w(TAG, "Unsupported pssh version: " + parseFullAtomVersion);
            return null;
        }
        UUID uuid = new UUID(parsableByteArray.readLong(), parsableByteArray.readLong());
        if (parseFullAtomVersion == 1) {
            int readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
            uuidArr = new UUID[readUnsignedIntToInt];
            for (int i = 0; i < readUnsignedIntToInt; i++) {
                uuidArr[i] = new UUID(parsableByteArray.readLong(), parsableByteArray.readLong());
            }
        } else {
            uuidArr = null;
        }
        int readUnsignedIntToInt2 = parsableByteArray.readUnsignedIntToInt();
        int bytesLeft2 = parsableByteArray.bytesLeft();
        if (readUnsignedIntToInt2 != bytesLeft2) {
            Log.w(TAG, "Atom data size (" + readUnsignedIntToInt2 + ") does not match the bytes left: " + bytesLeft2);
            return null;
        }
        byte[] bArr2 = new byte[readUnsignedIntToInt2];
        parsableByteArray.readBytes(bArr2, 0, readUnsignedIntToInt2);
        return new PsshAtom(uuid, parseFullAtomVersion, bArr2, uuidArr);
    }

    /* loaded from: classes.dex */
    public static final class PsshAtom {
        public final UUID[] keyIds;
        public final byte[] schemeData;
        public final UUID uuid;
        public final int version;

        PsshAtom(UUID uuid, int i, byte[] bArr, UUID[] uuidArr) {
            this.uuid = uuid;
            this.version = i;
            this.schemeData = bArr;
            this.keyIds = uuidArr;
        }
    }
}

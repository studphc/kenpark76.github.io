package androidx.media3.exoplayer.audio;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioDeviceInfo;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.net.Uri;
import android.provider.Settings;
import android.util.Pair;
import android.util.SparseArray;
import androidx.media3.common.AudioAttributes;
import androidx.media3.common.Format;
import androidx.media3.common.MimeTypes;
import androidx.media3.common.util.Assertions;
import androidx.media3.common.util.Util;
import androidx.media3.common.util.Util$$ExternalSyntheticApiModelOutline0;
import androidx.media3.exoplayer.drm.FrameworkMediaDrm$$ExternalSyntheticApiModelOutline0;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.primitives.Ints;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
/* loaded from: classes.dex */
public final class AudioCapabilities {
    static final int DEFAULT_MAX_CHANNEL_COUNT = 10;
    static final int DEFAULT_SAMPLE_RATE_HZ = 48000;
    private static final String EXTERNAL_SURROUND_SOUND_KEY = "external_surround_sound_enabled";
    private static final String FORCE_EXTERNAL_SURROUND_SOUND_KEY = "use_external_surround_sound_flag";
    private final SparseArray<AudioProfile> encodingToAudioProfile;
    private final int maxChannelCount;
    public static final AudioCapabilities DEFAULT_AUDIO_CAPABILITIES = new AudioCapabilities(ImmutableList.of(AudioProfile.DEFAULT_AUDIO_PROFILE));
    private static final ImmutableList<Integer> EXTERNAL_SURROUND_SOUND_ENCODINGS = ImmutableList.of(2, 5, 6);
    static final ImmutableMap<Integer, Integer> ALL_SURROUND_ENCODINGS_AND_MAX_CHANNELS = new ImmutableMap.Builder().put(5, 6).put(17, 6).put(7, 6).put(30, 10).put(18, 6).put(6, 8).put(8, 8).put(14, 8).buildOrThrow();

    @Deprecated
    public static AudioCapabilities getCapabilities(Context context) {
        return getCapabilities(context, AudioAttributes.DEFAULT, null);
    }

    public static AudioCapabilities getCapabilities(Context context, AudioAttributes audioAttributes, AudioDeviceInfo audioDeviceInfo) {
        return getCapabilitiesInternal(context, audioAttributes, (Util.SDK_INT < 23 || audioDeviceInfo == null) ? null : new AudioDeviceInfoApi23(audioDeviceInfo));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static AudioCapabilities getCapabilitiesInternal(Context context, AudioAttributes audioAttributes, AudioDeviceInfoApi23 audioDeviceInfoApi23) {
        return getCapabilitiesInternal(context, context.registerReceiver(null, new IntentFilter("android.media.action.HDMI_AUDIO_PLUG")), audioAttributes, audioDeviceInfoApi23);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static AudioCapabilities getCapabilitiesInternal(Context context, Intent intent, AudioAttributes audioAttributes, AudioDeviceInfoApi23 audioDeviceInfoApi23) {
        AudioManager audioManager = (AudioManager) Assertions.checkNotNull(context.getSystemService("audio"));
        if (audioDeviceInfoApi23 == null) {
            audioDeviceInfoApi23 = Util.SDK_INT >= 33 ? Api33.getDefaultRoutedDeviceForAttributes(audioManager, audioAttributes) : null;
        }
        if (Util.SDK_INT >= 33 && (Util.isTv(context) || Util.isAutomotive(context))) {
            return Api33.getCapabilitiesInternalForDirectPlayback(audioManager, audioAttributes);
        }
        if (Util.SDK_INT >= 23 && Api23.isBluetoothConnected(audioManager, audioDeviceInfoApi23)) {
            return DEFAULT_AUDIO_CAPABILITIES;
        }
        ImmutableSet.Builder builder = new ImmutableSet.Builder();
        builder.add((ImmutableSet.Builder) 2);
        if (Util.SDK_INT >= 29 && (Util.isTv(context) || Util.isAutomotive(context))) {
            builder.addAll((Iterable) Api29.getDirectPlaybackSupportedEncodings(audioAttributes));
            return new AudioCapabilities(getAudioProfiles(Ints.toArray(builder.build()), 10));
        }
        ContentResolver contentResolver = context.getContentResolver();
        boolean z = Settings.Global.getInt(contentResolver, FORCE_EXTERNAL_SURROUND_SOUND_KEY, 0) == 1;
        if ((z || deviceMaySetExternalSurroundSoundGlobalSetting()) && Settings.Global.getInt(contentResolver, EXTERNAL_SURROUND_SOUND_KEY, 0) == 1) {
            builder.addAll((Iterable) EXTERNAL_SURROUND_SOUND_ENCODINGS);
        }
        if (intent != null && !z && intent.getIntExtra("android.media.extra.AUDIO_PLUG_STATE", 0) == 1) {
            int[] intArrayExtra = intent.getIntArrayExtra("android.media.extra.ENCODINGS");
            if (intArrayExtra != null) {
                builder.addAll((Iterable) Ints.asList(intArrayExtra));
            }
            return new AudioCapabilities(getAudioProfiles(Ints.toArray(builder.build()), intent.getIntExtra("android.media.extra.MAX_CHANNEL_COUNT", 10)));
        }
        return new AudioCapabilities(getAudioProfiles(Ints.toArray(builder.build()), 10));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Uri getExternalSurroundSoundGlobalSettingUri() {
        if (deviceMaySetExternalSurroundSoundGlobalSetting()) {
            return Settings.Global.getUriFor(EXTERNAL_SURROUND_SOUND_KEY);
        }
        return null;
    }

    @Deprecated
    public AudioCapabilities(int[] iArr, int i) {
        this(getAudioProfiles(iArr, i));
    }

    private AudioCapabilities(List<AudioProfile> list) {
        this.encodingToAudioProfile = new SparseArray<>();
        for (int i = 0; i < list.size(); i++) {
            AudioProfile audioProfile = list.get(i);
            this.encodingToAudioProfile.put(audioProfile.encoding, audioProfile);
        }
        int i2 = 0;
        for (int i3 = 0; i3 < this.encodingToAudioProfile.size(); i3++) {
            i2 = Math.max(i2, this.encodingToAudioProfile.valueAt(i3).maxChannelCount);
        }
        this.maxChannelCount = i2;
    }

    public boolean supportsEncoding(int i) {
        return Util.contains(this.encodingToAudioProfile, i);
    }

    public int getMaxChannelCount() {
        return this.maxChannelCount;
    }

    @Deprecated
    public boolean isPassthroughPlaybackSupported(Format format) {
        return isPassthroughPlaybackSupported(format, AudioAttributes.DEFAULT);
    }

    public boolean isPassthroughPlaybackSupported(Format format, AudioAttributes audioAttributes) {
        return getEncodingAndChannelConfigForPassthrough(format, audioAttributes) != null;
    }

    @Deprecated
    public Pair<Integer, Integer> getEncodingAndChannelConfigForPassthrough(Format format) {
        return getEncodingAndChannelConfigForPassthrough(format, AudioAttributes.DEFAULT);
    }

    public Pair<Integer, Integer> getEncodingAndChannelConfigForPassthrough(Format format, AudioAttributes audioAttributes) {
        int maxSupportedChannelCountForPassthrough;
        int encoding = MimeTypes.getEncoding((String) Assertions.checkNotNull(format.sampleMimeType), format.codecs);
        if (ALL_SURROUND_ENCODINGS_AND_MAX_CHANNELS.containsKey(Integer.valueOf(encoding))) {
            if (encoding == 18 && !supportsEncoding(18)) {
                encoding = 6;
            } else if ((encoding == 8 && !supportsEncoding(8)) || (encoding == 30 && !supportsEncoding(30))) {
                encoding = 7;
            }
            if (supportsEncoding(encoding)) {
                AudioProfile audioProfile = (AudioProfile) Assertions.checkNotNull(this.encodingToAudioProfile.get(encoding));
                if (format.channelCount == -1 || encoding == 18) {
                    maxSupportedChannelCountForPassthrough = audioProfile.getMaxSupportedChannelCountForPassthrough(format.sampleRate != -1 ? format.sampleRate : 48000, audioAttributes);
                } else {
                    maxSupportedChannelCountForPassthrough = format.channelCount;
                    if (!format.sampleMimeType.equals(MimeTypes.AUDIO_DTS_X) || Util.SDK_INT >= 33) {
                        if (!audioProfile.supportsChannelCount(maxSupportedChannelCountForPassthrough)) {
                            return null;
                        }
                    } else if (maxSupportedChannelCountForPassthrough > 10) {
                        return null;
                    }
                }
                int channelConfigForPassthrough = getChannelConfigForPassthrough(maxSupportedChannelCountForPassthrough);
                if (channelConfigForPassthrough == 0) {
                    return null;
                }
                return Pair.create(Integer.valueOf(encoding), Integer.valueOf(channelConfigForPassthrough));
            }
            return null;
        }
        return null;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof AudioCapabilities) {
            AudioCapabilities audioCapabilities = (AudioCapabilities) obj;
            return Util.contentEquals(this.encodingToAudioProfile, audioCapabilities.encodingToAudioProfile) && this.maxChannelCount == audioCapabilities.maxChannelCount;
        }
        return false;
    }

    public int hashCode() {
        return this.maxChannelCount + (Util.contentHashCode(this.encodingToAudioProfile) * 31);
    }

    public String toString() {
        return "AudioCapabilities[maxChannelCount=" + this.maxChannelCount + ", audioProfiles=" + this.encodingToAudioProfile + "]";
    }

    private static boolean deviceMaySetExternalSurroundSoundGlobalSetting() {
        return Util.SDK_INT >= 17 && ("Amazon".equals(Util.MANUFACTURER) || "Xiaomi".equals(Util.MANUFACTURER));
    }

    private static int getChannelConfigForPassthrough(int i) {
        if (Util.SDK_INT <= 28) {
            if (i == 7) {
                i = 8;
            } else if (i == 3 || i == 4 || i == 5) {
                i = 6;
            }
        }
        if (Util.SDK_INT <= 26 && "fugu".equals(Util.DEVICE) && i == 1) {
            i = 2;
        }
        return Util.getAudioTrackChannelConfig(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ImmutableList<AudioProfile> getAudioProfiles(List<android.media.AudioProfile> list) {
        int encapsulationType;
        int format;
        int[] channelMasks;
        int[] channelMasks2;
        HashMap hashMap = new HashMap();
        hashMap.put(2, new HashSet(Ints.asList(12)));
        for (int i = 0; i < list.size(); i++) {
            android.media.AudioProfile m = Util$$ExternalSyntheticApiModelOutline0.m((Object) list.get(i));
            encapsulationType = m.getEncapsulationType();
            if (encapsulationType != 1) {
                format = m.getFormat();
                if (Util.isEncodingLinearPcm(format) || ALL_SURROUND_ENCODINGS_AND_MAX_CHANNELS.containsKey(Integer.valueOf(format))) {
                    if (hashMap.containsKey(Integer.valueOf(format))) {
                        channelMasks2 = m.getChannelMasks();
                        ((Set) Assertions.checkNotNull((Set) hashMap.get(Integer.valueOf(format)))).addAll(Ints.asList(channelMasks2));
                    } else {
                        Integer valueOf = Integer.valueOf(format);
                        channelMasks = m.getChannelMasks();
                        hashMap.put(valueOf, new HashSet(Ints.asList(channelMasks)));
                    }
                }
            }
        }
        ImmutableList.Builder builder = ImmutableList.builder();
        for (Map.Entry entry : hashMap.entrySet()) {
            builder.add((ImmutableList.Builder) new AudioProfile(((Integer) entry.getKey()).intValue(), (Set) entry.getValue()));
        }
        return builder.build();
    }

    private static ImmutableList<AudioProfile> getAudioProfiles(int[] iArr, int i) {
        ImmutableList.Builder builder = ImmutableList.builder();
        if (iArr == null) {
            iArr = new int[0];
        }
        for (int i2 : iArr) {
            builder.add((ImmutableList.Builder) new AudioProfile(i2, i));
        }
        return builder.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class AudioProfile {
        public static final AudioProfile DEFAULT_AUDIO_PROFILE;
        private final ImmutableSet<Integer> channelMasks;
        public final int encoding;
        public final int maxChannelCount;

        static {
            AudioProfile audioProfile;
            if (Util.SDK_INT >= 33) {
                audioProfile = new AudioProfile(2, getAllChannelMasksForMaxChannelCount(10));
            } else {
                audioProfile = new AudioProfile(2, 10);
            }
            DEFAULT_AUDIO_PROFILE = audioProfile;
        }

        public AudioProfile(int i, Set<Integer> set) {
            this.encoding = i;
            ImmutableSet<Integer> copyOf = ImmutableSet.copyOf((Collection) set);
            this.channelMasks = copyOf;
            UnmodifiableIterator<Integer> it = copyOf.iterator();
            int i2 = 0;
            while (it.hasNext()) {
                i2 = Math.max(i2, Integer.bitCount(it.next().intValue()));
            }
            this.maxChannelCount = i2;
        }

        public AudioProfile(int i, int i2) {
            this.encoding = i;
            this.maxChannelCount = i2;
            this.channelMasks = null;
        }

        public boolean supportsChannelCount(int i) {
            if (this.channelMasks == null) {
                return i <= this.maxChannelCount;
            }
            int audioTrackChannelConfig = Util.getAudioTrackChannelConfig(i);
            if (audioTrackChannelConfig == 0) {
                return false;
            }
            return this.channelMasks.contains(Integer.valueOf(audioTrackChannelConfig));
        }

        public int getMaxSupportedChannelCountForPassthrough(int i, AudioAttributes audioAttributes) {
            if (this.channelMasks != null) {
                return this.maxChannelCount;
            }
            if (Util.SDK_INT >= 29) {
                return Api29.getMaxSupportedChannelCountForPassthrough(this.encoding, i, audioAttributes);
            }
            return ((Integer) Assertions.checkNotNull(AudioCapabilities.ALL_SURROUND_ENCODINGS_AND_MAX_CHANNELS.getOrDefault(Integer.valueOf(this.encoding), 0))).intValue();
        }

        private static ImmutableSet<Integer> getAllChannelMasksForMaxChannelCount(int i) {
            ImmutableSet.Builder builder = new ImmutableSet.Builder();
            for (int i2 = 1; i2 <= i; i2++) {
                builder.add((ImmutableSet.Builder) Integer.valueOf(Util.getAudioTrackChannelConfig(i2)));
            }
            return builder.build();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof AudioProfile) {
                AudioProfile audioProfile = (AudioProfile) obj;
                return this.encoding == audioProfile.encoding && this.maxChannelCount == audioProfile.maxChannelCount && Util.areEqual(this.channelMasks, audioProfile.channelMasks);
            }
            return false;
        }

        public int hashCode() {
            int i = ((this.encoding * 31) + this.maxChannelCount) * 31;
            ImmutableSet<Integer> immutableSet = this.channelMasks;
            return i + (immutableSet == null ? 0 : immutableSet.hashCode());
        }

        public String toString() {
            return "AudioProfile[format=" + this.encoding + ", maxChannelCount=" + this.maxChannelCount + ", channelMasks=" + this.channelMasks + "]";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class Api23 {
        private Api23() {
        }

        public static boolean isBluetoothConnected(AudioManager audioManager, AudioDeviceInfoApi23 audioDeviceInfoApi23) {
            int type;
            AudioDeviceInfo[] devices = audioDeviceInfoApi23 == null ? ((AudioManager) Assertions.checkNotNull(audioManager)).getDevices(2) : new AudioDeviceInfo[]{audioDeviceInfoApi23.audioDeviceInfo};
            ImmutableSet<Integer> allBluetoothDeviceTypes = getAllBluetoothDeviceTypes();
            for (AudioDeviceInfo audioDeviceInfo : devices) {
                type = audioDeviceInfo.getType();
                if (allBluetoothDeviceTypes.contains(Integer.valueOf(type))) {
                    return true;
                }
            }
            return false;
        }

        private static ImmutableSet<Integer> getAllBluetoothDeviceTypes() {
            ImmutableSet.Builder add = new ImmutableSet.Builder().add((Object[]) new Integer[]{8, 7});
            if (Util.SDK_INT >= 31) {
                add.add((Object[]) new Integer[]{26, 27});
            }
            if (Util.SDK_INT >= 33) {
                add.add((ImmutableSet.Builder) 30);
            }
            return add.build();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class Api29 {
        private Api29() {
        }

        public static ImmutableList<Integer> getDirectPlaybackSupportedEncodings(AudioAttributes audioAttributes) {
            boolean isDirectPlaybackSupported;
            ImmutableList.Builder builder = ImmutableList.builder();
            UnmodifiableIterator<Integer> it = AudioCapabilities.ALL_SURROUND_ENCODINGS_AND_MAX_CHANNELS.keySet().iterator();
            while (it.hasNext()) {
                int intValue = it.next().intValue();
                if (Util.SDK_INT >= Util.getApiLevelThatAudioFormatIntroducedAudioEncoding(intValue)) {
                    isDirectPlaybackSupported = AudioTrack.isDirectPlaybackSupported(new AudioFormat.Builder().setChannelMask(12).setEncoding(intValue).setSampleRate(48000).build(), audioAttributes.getAudioAttributesV21().audioAttributes);
                    if (isDirectPlaybackSupported) {
                        builder.add((ImmutableList.Builder) Integer.valueOf(intValue));
                    }
                }
            }
            builder.add((ImmutableList.Builder) 2);
            return builder.build();
        }

        public static int getMaxSupportedChannelCountForPassthrough(int i, int i2, AudioAttributes audioAttributes) {
            boolean isDirectPlaybackSupported;
            for (int i3 = 10; i3 > 0; i3--) {
                int audioTrackChannelConfig = Util.getAudioTrackChannelConfig(i3);
                if (audioTrackChannelConfig != 0) {
                    isDirectPlaybackSupported = AudioTrack.isDirectPlaybackSupported(new AudioFormat.Builder().setEncoding(i).setSampleRate(i2).setChannelMask(audioTrackChannelConfig).build(), audioAttributes.getAudioAttributesV21().audioAttributes);
                    if (isDirectPlaybackSupported) {
                        return i3;
                    }
                }
            }
            return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static final class Api33 {
        private Api33() {
        }

        public static AudioCapabilities getCapabilitiesInternalForDirectPlayback(AudioManager audioManager, AudioAttributes audioAttributes) {
            List directProfilesForAttributes;
            directProfilesForAttributes = audioManager.getDirectProfilesForAttributes(audioAttributes.getAudioAttributesV21().audioAttributes);
            return new AudioCapabilities(AudioCapabilities.getAudioProfiles(directProfilesForAttributes));
        }

        public static AudioDeviceInfoApi23 getDefaultRoutedDeviceForAttributes(AudioManager audioManager, AudioAttributes audioAttributes) {
            List audioDevicesForAttributes;
            try {
                audioDevicesForAttributes = ((AudioManager) Assertions.checkNotNull(audioManager)).getAudioDevicesForAttributes(audioAttributes.getAudioAttributesV21().audioAttributes);
                if (audioDevicesForAttributes.isEmpty()) {
                    return null;
                }
                return new AudioDeviceInfoApi23(FrameworkMediaDrm$$ExternalSyntheticApiModelOutline0.m(audioDevicesForAttributes.get(0)));
            } catch (RuntimeException unused) {
                return null;
            }
        }
    }
}

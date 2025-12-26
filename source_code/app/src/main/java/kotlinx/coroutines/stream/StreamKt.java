package kotlinx.coroutines.stream;

import androidx.exifinterface.media.ExifInterface;
import j$.util.stream.Stream;
import kotlin.Metadata;
import kotlinx.coroutines.flow.Flow;
/* compiled from: Stream.kt */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001c\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0002\"\u0004\b\u0000\u0010\u0000*\b\u0012\u0004\u0012\u00028\u00000\u0001¨\u0006\u0004"}, d2 = {ExifInterface.GPS_DIRECTION_TRUE, "j$/util/stream/Stream", "Lkotlinx/coroutines/flow/Flow;", "consumeAsFlow", "kotlinx-coroutines-core"}, k = 2, mv = {1, 9, 0})
/* loaded from: classes3.dex */
public final class StreamKt {
    public static final <T> Flow<T> consumeAsFlow(Stream<T> stream) {
        return new StreamFlow(stream);
    }
}

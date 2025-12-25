package retrofit2;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import javax.annotation.Nullable;
/* loaded from: classes3.dex */
final class DefaultMethodSupport {
    @Nullable
    private static Constructor<MethodHandles.Lookup> lookupConstructor;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public static Object invoke(Method method, Class<?> cls, Object obj, @Nullable Object[] objArr) throws Throwable {
        MethodHandle unreflectSpecial;
        MethodHandle bindTo;
        Object invokeWithArguments;
        Constructor<MethodHandles.Lookup> constructor = lookupConstructor;
        if (constructor == null) {
            constructor = Reflection$Java8$$ExternalSyntheticApiModelOutline0.m$4().getDeclaredConstructor(Class.class, Integer.TYPE);
            constructor.setAccessible(true);
            lookupConstructor = constructor;
        }
        unreflectSpecial = Reflection$Java8$$ExternalSyntheticApiModelOutline0.m((Object) constructor.newInstance(cls, -1)).unreflectSpecial(method, cls);
        bindTo = unreflectSpecial.bindTo(obj);
        invokeWithArguments = bindTo.invokeWithArguments(objArr);
        return invokeWithArguments;
    }

    private DefaultMethodSupport() {
    }
}

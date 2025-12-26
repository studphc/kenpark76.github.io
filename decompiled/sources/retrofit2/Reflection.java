package retrofit2;

import android.os.Build;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import javax.annotation.Nullable;
/* loaded from: classes3.dex */
class Reflection {
    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isDefaultMethod(Method method) {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public Object invokeDefaultMethod(Method method, Class<?> cls, Object obj, @Nullable Object[] objArr) throws Throwable {
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String describeMethodParameter(Method method, int i) {
        return "parameter #" + (i + 1);
    }

    /* loaded from: classes3.dex */
    static class Java8 extends Reflection {
        @Override // retrofit2.Reflection
        boolean isDefaultMethod(Method method) {
            boolean isDefault;
            isDefault = method.isDefault();
            return isDefault;
        }

        @Override // retrofit2.Reflection
        Object invokeDefaultMethod(Method method, Class<?> cls, Object obj, @Nullable Object[] objArr) throws Throwable {
            return DefaultMethodSupport.invoke(method, cls, obj, objArr);
        }

        @Override // retrofit2.Reflection
        String describeMethodParameter(Method method, int i) {
            Parameter[] parameters;
            boolean isNamePresent;
            String name;
            parameters = method.getParameters();
            Parameter parameter = parameters[i];
            isNamePresent = parameter.isNamePresent();
            if (isNamePresent) {
                StringBuilder sb = new StringBuilder("parameter '");
                name = parameter.getName();
                sb.append(name);
                sb.append('\'');
                return sb.toString();
            }
            return super.describeMethodParameter(method, i);
        }
    }

    /* loaded from: classes3.dex */
    static final class Android24 extends Reflection {
        @Override // retrofit2.Reflection
        boolean isDefaultMethod(Method method) {
            boolean isDefault;
            isDefault = method.isDefault();
            return isDefault;
        }

        @Override // retrofit2.Reflection
        Object invokeDefaultMethod(Method method, Class<?> cls, Object obj, @Nullable Object[] objArr) throws Throwable {
            if (Build.VERSION.SDK_INT < 26) {
                throw new UnsupportedOperationException("Calling default methods on API 24 and 25 is not supported");
            }
            return DefaultMethodSupport.invoke(method, cls, obj, objArr);
        }
    }
}

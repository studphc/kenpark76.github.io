package retrofit2;

import j$.util.Objects;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
/* loaded from: classes3.dex */
public final class Invocation {
    private final List<?> arguments;
    @Nullable
    private final Object instance;
    private final Method method;
    private final Class<?> service;

    public static <T> Invocation of(Class<T> cls, T t, Method method, List<?> list) {
        Objects.requireNonNull(cls, "service == null");
        Objects.requireNonNull(t, "instance == null");
        Objects.requireNonNull(method, "method == null");
        Objects.requireNonNull(list, "arguments == null");
        return new Invocation(cls, t, method, new ArrayList(list));
    }

    @Deprecated
    public static Invocation of(Method method, List<?> list) {
        Objects.requireNonNull(method, "method == null");
        Objects.requireNonNull(list, "arguments == null");
        return new Invocation(method.getDeclaringClass(), null, method, new ArrayList(list));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Invocation(Class<?> cls, @Nullable Object obj, Method method, List<?> list) {
        this.service = cls;
        this.instance = obj;
        this.method = method;
        this.arguments = Collections.unmodifiableList(list);
    }

    public Class<?> service() {
        return this.service;
    }

    @Nullable
    public Object instance() {
        return this.instance;
    }

    public Method method() {
        return this.method;
    }

    public List<?> arguments() {
        return this.arguments;
    }

    public String toString() {
        return String.format("%s.%s() %s", this.service.getName(), this.method.getName(), this.arguments);
    }
}

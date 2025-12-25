package kotlin.io.path;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.tracing.Trace$$ExternalSyntheticApiModelOutline0;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystemException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.SecureDirectoryStream;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttributeView;
import java.util.Arrays;
import java.util.List;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SpreadBuilder;
import retrofit2.Reflection$Java8$$ExternalSyntheticApiModelOutline0;
/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: PathRecursiveFunctions.kt */
@Metadata(d1 = {"\u0000v\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a$\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0082\b¢\u0006\u0002\b\u0006\u001a\u001d\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002¢\u0006\u0002\b\n\u001a\u001d\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002¢\u0006\u0002\b\r\u001a&\u0010\u000e\u001a\u0004\u0018\u0001H\u000f\"\u0004\b\u0000\u0010\u000f2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u000f0\u0005H\u0082\b¢\u0006\u0004\b\u0010\u0010\u0011\u001aw\u0010\u0012\u001a\u00020\t*\u00020\t2\u0006\u0010\u0013\u001a\u00020\t2Q\b\u0002\u0010\u0014\u001aK\u0012\u0013\u0012\u00110\t¢\u0006\f\b\u0016\u0012\b\b\u0017\u0012\u0004\b\b(\u0018\u0012\u0013\u0012\u00110\t¢\u0006\f\b\u0016\u0012\b\b\u0017\u0012\u0004\b\b(\u0013\u0012\u0017\u0012\u00150\u0019j\u0002`\u001a¢\u0006\f\b\u0016\u0012\b\b\u0017\u0012\u0004\b\b(\u001b\u0012\u0004\u0012\u00020\u001c0\u00152\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001eH\u0007\u001a´\u0001\u0010\u0012\u001a\u00020\t*\u00020\t2\u0006\u0010\u0013\u001a\u00020\t2Q\b\u0002\u0010\u0014\u001aK\u0012\u0013\u0012\u00110\t¢\u0006\f\b\u0016\u0012\b\b\u0017\u0012\u0004\b\b(\u0018\u0012\u0013\u0012\u00110\t¢\u0006\f\b\u0016\u0012\b\b\u0017\u0012\u0004\b\b(\u0013\u0012\u0017\u0012\u00150\u0019j\u0002`\u001a¢\u0006\f\b\u0016\u0012\b\b\u0017\u0012\u0004\b\b(\u001b\u0012\u0004\u0012\u00020\u001c0\u00152\u0006\u0010\u001d\u001a\u00020\u001e2C\b\u0002\u0010 \u001a=\u0012\u0004\u0012\u00020!\u0012\u0013\u0012\u00110\t¢\u0006\f\b\u0016\u0012\b\b\u0017\u0012\u0004\b\b(\u0018\u0012\u0013\u0012\u00110\t¢\u0006\f\b\u0016\u0012\b\b\u0017\u0012\u0004\b\b(\u0013\u0012\u0004\u0012\u00020\"0\u0015¢\u0006\u0002\b#H\u0007\u001a\f\u0010$\u001a\u00020\u0001*\u00020\tH\u0007\u001a\u001b\u0010%\u001a\f\u0012\b\u0012\u00060\u0019j\u0002`\u001a0&*\u00020\tH\u0002¢\u0006\u0002\b'\u001a'\u0010(\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\t0)2\u0006\u0010\u0017\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002¢\u0006\u0002\b*\u001a'\u0010+\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\t0)2\u0006\u0010\u0017\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002¢\u0006\u0002\b,\u001a5\u0010-\u001a\u00020\u001e*\b\u0012\u0004\u0012\u00020\t0)2\u0006\u0010.\u001a\u00020\t2\u0012\u0010/\u001a\n\u0012\u0006\b\u0001\u0012\u00020100\"\u000201H\u0002¢\u0006\u0004\b2\u00103\u001a\u0011\u00104\u001a\u000205*\u00020\"H\u0003¢\u0006\u0002\b6\u001a\u0011\u00104\u001a\u000205*\u00020\u001cH\u0003¢\u0006\u0002\b6¨\u00067"}, d2 = {"collectIfThrows", "", "collector", "Lkotlin/io/path/ExceptionsCollector;", "function", "Lkotlin/Function0;", "collectIfThrows$PathsKt__PathRecursiveFunctionsKt", "insecureEnterDirectory", "path", "Ljava/nio/file/Path;", "insecureEnterDirectory$PathsKt__PathRecursiveFunctionsKt", "insecureHandleEntry", "entry", "insecureHandleEntry$PathsKt__PathRecursiveFunctionsKt", "tryIgnoreNoSuchFileException", "R", "tryIgnoreNoSuchFileException$PathsKt__PathRecursiveFunctionsKt", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "copyToRecursively", TypedValues.AttributesType.S_TARGET, "onError", "Lkotlin/Function3;", "Lkotlin/ParameterName;", "name", "source", "Ljava/lang/Exception;", "Lkotlin/Exception;", "exception", "Lkotlin/io/path/OnErrorResult;", "followLinks", "", "overwrite", "copyAction", "Lkotlin/io/path/CopyActionContext;", "Lkotlin/io/path/CopyActionResult;", "Lkotlin/ExtensionFunctionType;", "deleteRecursively", "deleteRecursivelyImpl", "", "deleteRecursivelyImpl$PathsKt__PathRecursiveFunctionsKt", "enterDirectory", "Ljava/nio/file/SecureDirectoryStream;", "enterDirectory$PathsKt__PathRecursiveFunctionsKt", "handleEntry", "handleEntry$PathsKt__PathRecursiveFunctionsKt", "isDirectory", "entryName", "options", "", "Ljava/nio/file/LinkOption;", "isDirectory$PathsKt__PathRecursiveFunctionsKt", "(Ljava/nio/file/SecureDirectoryStream;Ljava/nio/file/Path;[Ljava/nio/file/LinkOption;)Z", "toFileVisitResult", "Ljava/nio/file/FileVisitResult;", "toFileVisitResult$PathsKt__PathRecursiveFunctionsKt", "kotlin-stdlib-jdk7"}, k = 5, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_EDITOR_ABSOLUTEX, xs = "kotlin/io/path/PathsKt")
/* loaded from: classes3.dex */
public class PathsKt__PathRecursiveFunctionsKt extends PathsKt__PathReadWriteKt {

    /* compiled from: PathRecursiveFunctions.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    /* loaded from: classes3.dex */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[CopyActionResult.values().length];
            try {
                iArr[CopyActionResult.CONTINUE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[CopyActionResult.TERMINATE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[CopyActionResult.SKIP_SUBTREE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[OnErrorResult.values().length];
            try {
                iArr2[OnErrorResult.TERMINATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[OnErrorResult.SKIP_SUBTREE.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public static /* synthetic */ Path copyToRecursively$default(Path path, Path path2, Function3 function3, boolean z, boolean z2, int i, Object obj) {
        if ((i & 2) != 0) {
            function3 = new Function3() { // from class: kotlin.io.path.PathsKt__PathRecursiveFunctionsKt$copyToRecursively$1
                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ Object invoke(Object obj2, Object obj3, Object obj4) {
                    return invoke(Trace$$ExternalSyntheticApiModelOutline0.m343m(obj2), Trace$$ExternalSyntheticApiModelOutline0.m343m(obj3), (Exception) obj4);
                }

                public final Void invoke(Path path3, Path path4, Exception exception) {
                    Intrinsics.checkNotNullParameter(path3, "<anonymous parameter 0>");
                    Intrinsics.checkNotNullParameter(path4, "<anonymous parameter 1>");
                    Intrinsics.checkNotNullParameter(exception, "exception");
                    throw exception;
                }
            };
        }
        return PathsKt.copyToRecursively(path, path2, function3, z, z2);
    }

    public static final Path copyToRecursively(Path path, Path target, Function3<? super Path, ? super Path, ? super Exception, ? extends OnErrorResult> onError, final boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(path, "<this>");
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(onError, "onError");
        if (z2) {
            return PathsKt.copyToRecursively(path, target, onError, z, new Function3<CopyActionContext, Path, Path, CopyActionResult>() { // from class: kotlin.io.path.PathsKt__PathRecursiveFunctionsKt$copyToRecursively$2
                /* JADX INFO: Access modifiers changed from: package-private */
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ CopyActionResult invoke(CopyActionContext copyActionContext, Path path2, Path path3) {
                    return invoke2(copyActionContext, Trace$$ExternalSyntheticApiModelOutline0.m343m((Object) path2), Trace$$ExternalSyntheticApiModelOutline0.m343m((Object) path3));
                }

                /* renamed from: invoke  reason: avoid collision after fix types in other method */
                public final CopyActionResult invoke2(CopyActionContext copyToRecursively, Path src, Path dst) {
                    LinkOption linkOption;
                    boolean isDirectory;
                    boolean isDirectory2;
                    StandardCopyOption standardCopyOption;
                    Path copy;
                    Intrinsics.checkNotNullParameter(copyToRecursively, "$this$copyToRecursively");
                    Intrinsics.checkNotNullParameter(src, "src");
                    Intrinsics.checkNotNullParameter(dst, "dst");
                    LinkOption[] linkOptions = LinkFollowing.INSTANCE.toLinkOptions(z);
                    linkOption = LinkOption.NOFOLLOW_LINKS;
                    isDirectory = Files.isDirectory(dst, (LinkOption[]) Arrays.copyOf(new LinkOption[]{linkOption}, 1));
                    LinkOption[] linkOptionArr = (LinkOption[]) Arrays.copyOf(linkOptions, linkOptions.length);
                    isDirectory2 = Files.isDirectory(src, (LinkOption[]) Arrays.copyOf(linkOptionArr, linkOptionArr.length));
                    if (!isDirectory2 || !isDirectory) {
                        if (isDirectory) {
                            PathsKt.deleteRecursively(dst);
                        }
                        SpreadBuilder spreadBuilder = new SpreadBuilder(2);
                        spreadBuilder.addSpread(linkOptions);
                        standardCopyOption = StandardCopyOption.REPLACE_EXISTING;
                        spreadBuilder.add(standardCopyOption);
                        CopyOption[] copyOptionArr = (CopyOption[]) spreadBuilder.toArray(new CopyOption[spreadBuilder.size()]);
                        copy = Files.copy(src, dst, (CopyOption[]) Arrays.copyOf(copyOptionArr, copyOptionArr.length));
                        Intrinsics.checkNotNullExpressionValue(copy, "copy(...)");
                    }
                    return CopyActionResult.CONTINUE;
                }
            });
        }
        return PathsKt.copyToRecursively$default(path, target, onError, z, (Function3) null, 8, (Object) null);
    }

    public static /* synthetic */ Path copyToRecursively$default(Path path, Path path2, Function3 function3, final boolean z, Function3 function32, int i, Object obj) {
        if ((i & 2) != 0) {
            function3 = new Function3() { // from class: kotlin.io.path.PathsKt__PathRecursiveFunctionsKt$copyToRecursively$3
                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ Object invoke(Object obj2, Object obj3, Object obj4) {
                    return invoke(Trace$$ExternalSyntheticApiModelOutline0.m343m(obj2), Trace$$ExternalSyntheticApiModelOutline0.m343m(obj3), (Exception) obj4);
                }

                public final Void invoke(Path path3, Path path4, Exception exception) {
                    Intrinsics.checkNotNullParameter(path3, "<anonymous parameter 0>");
                    Intrinsics.checkNotNullParameter(path4, "<anonymous parameter 1>");
                    Intrinsics.checkNotNullParameter(exception, "exception");
                    throw exception;
                }
            };
        }
        if ((i & 8) != 0) {
            function32 = new Function3<CopyActionContext, Path, Path, CopyActionResult>() { // from class: kotlin.io.path.PathsKt__PathRecursiveFunctionsKt$copyToRecursively$4
                /* JADX INFO: Access modifiers changed from: package-private */
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(3);
                }

                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ CopyActionResult invoke(CopyActionContext copyActionContext, Path path3, Path path4) {
                    return invoke2(copyActionContext, Trace$$ExternalSyntheticApiModelOutline0.m343m((Object) path3), Trace$$ExternalSyntheticApiModelOutline0.m343m((Object) path4));
                }

                /* renamed from: invoke  reason: avoid collision after fix types in other method */
                public final CopyActionResult invoke2(CopyActionContext copyActionContext, Path src, Path dst) {
                    Intrinsics.checkNotNullParameter(copyActionContext, "$this$null");
                    Intrinsics.checkNotNullParameter(src, "src");
                    Intrinsics.checkNotNullParameter(dst, "dst");
                    return copyActionContext.copyToIgnoringExistingDirectory(src, dst, z);
                }
            };
        }
        return PathsKt.copyToRecursively(path, path2, function3, z, function32);
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0063, code lost:
        if (r3 == false) goto L16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0043, code lost:
        if (r1 == false) goto L8;
     */
    /* JADX WARN: Removed duplicated region for block: B:16:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00b1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final java.nio.file.Path copyToRecursively(final java.nio.file.Path r6, final java.nio.file.Path r7, final kotlin.jvm.functions.Function3<? super java.nio.file.Path, ? super java.nio.file.Path, ? super java.lang.Exception, ? extends kotlin.io.path.OnErrorResult> r8, boolean r9, final kotlin.jvm.functions.Function3<? super kotlin.io.path.CopyActionContext, ? super java.nio.file.Path, ? super java.nio.file.Path, ? extends kotlin.io.path.CopyActionResult> r10) {
        /*
            java.lang.String r0 = "<this>"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r6, r0)
            java.lang.String r0 = "target"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r7, r0)
            java.lang.String r0 = "onError"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r8, r0)
            java.lang.String r0 = "copyAction"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r10, r0)
            kotlin.io.path.LinkFollowing r0 = kotlin.io.path.LinkFollowing.INSTANCE
            java.nio.file.LinkOption[] r0 = r0.toLinkOptions(r9)
            int r1 = r0.length
            java.lang.Object[] r0 = java.util.Arrays.copyOf(r0, r1)
            java.nio.file.LinkOption[] r0 = (java.nio.file.LinkOption[]) r0
            int r1 = r0.length
            java.lang.Object[] r0 = java.util.Arrays.copyOf(r0, r1)
            java.nio.file.LinkOption[] r0 = (java.nio.file.LinkOption[]) r0
            boolean r0 = androidx.tracing.Trace$$ExternalSyntheticApiModelOutline0.m$1(r6, r0)
            if (r0 == 0) goto Ld4
            r0 = 0
            java.nio.file.LinkOption[] r1 = new java.nio.file.LinkOption[r0]
            java.lang.Object[] r1 = java.util.Arrays.copyOf(r1, r0)
            java.nio.file.LinkOption[] r1 = (java.nio.file.LinkOption[]) r1
            boolean r1 = androidx.tracing.Trace$$ExternalSyntheticApiModelOutline0.m$1(r6, r1)
            if (r1 == 0) goto Lc3
            if (r9 != 0) goto L45
            boolean r1 = retrofit2.Reflection$Java8$$ExternalSyntheticApiModelOutline0.m2140m(r6)
            if (r1 != 0) goto Lc3
        L45:
            java.nio.file.LinkOption[] r1 = new java.nio.file.LinkOption[r0]
            java.lang.Object[] r1 = java.util.Arrays.copyOf(r1, r0)
            java.nio.file.LinkOption[] r1 = (java.nio.file.LinkOption[]) r1
            boolean r1 = androidx.tracing.Trace$$ExternalSyntheticApiModelOutline0.m$1(r7, r1)
            r2 = 1
            if (r1 == 0) goto L5c
            boolean r1 = retrofit2.Reflection$Java8$$ExternalSyntheticApiModelOutline0.m2140m(r7)
            if (r1 != 0) goto L5c
            r1 = 1
            goto L5d
        L5c:
            r1 = 0
        L5d:
            if (r1 == 0) goto L65
            boolean r3 = androidx.tracing.Trace$$ExternalSyntheticApiModelOutline0.m357m(r6, r7)
            if (r3 != 0) goto Lc3
        L65:
            java.nio.file.FileSystem r3 = androidx.tracing.Trace$$ExternalSyntheticApiModelOutline0.m336m(r6)
            java.nio.file.FileSystem r4 = androidx.tracing.Trace$$ExternalSyntheticApiModelOutline0.m336m(r7)
            boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual(r3, r4)
            if (r3 != 0) goto L74
            goto Lae
        L74:
            if (r1 == 0) goto L87
            java.nio.file.LinkOption[] r1 = new java.nio.file.LinkOption[r0]
            java.nio.file.Path r1 = retrofit2.Reflection$Java8$$ExternalSyntheticApiModelOutline0.m(r7, r1)
            java.nio.file.LinkOption[] r0 = new java.nio.file.LinkOption[r0]
            java.nio.file.Path r0 = retrofit2.Reflection$Java8$$ExternalSyntheticApiModelOutline0.m(r6, r0)
            boolean r0 = retrofit2.Reflection$Java8$$ExternalSyntheticApiModelOutline0.m2141m(r1, r0)
            goto Lae
        L87:
            java.nio.file.Path r1 = androidx.tracing.Trace$$ExternalSyntheticApiModelOutline0.m$1(r7)
            if (r1 == 0) goto Lae
            java.nio.file.LinkOption[] r3 = new java.nio.file.LinkOption[r0]
            java.lang.Object[] r3 = java.util.Arrays.copyOf(r3, r0)
            java.nio.file.LinkOption[] r3 = (java.nio.file.LinkOption[]) r3
            boolean r3 = androidx.tracing.Trace$$ExternalSyntheticApiModelOutline0.m$1(r1, r3)
            if (r3 == 0) goto Lae
            java.nio.file.LinkOption[] r3 = new java.nio.file.LinkOption[r0]
            java.nio.file.Path r1 = retrofit2.Reflection$Java8$$ExternalSyntheticApiModelOutline0.m(r1, r3)
            java.nio.file.LinkOption[] r3 = new java.nio.file.LinkOption[r0]
            java.nio.file.Path r3 = retrofit2.Reflection$Java8$$ExternalSyntheticApiModelOutline0.m(r6, r3)
            boolean r1 = retrofit2.Reflection$Java8$$ExternalSyntheticApiModelOutline0.m2141m(r1, r3)
            if (r1 == 0) goto Lae
            r0 = 1
        Lae:
            if (r0 != 0) goto Lb1
            goto Lc3
        Lb1:
            androidx.tracing.Trace$$ExternalSyntheticApiModelOutline0.m351m()
            java.lang.String r6 = r6.toString()
            java.lang.String r7 = r7.toString()
            java.lang.String r8 = "Recursively copying a directory into its subdirectory is prohibited."
            java.nio.file.FileSystemException r6 = retrofit2.Reflection$Java8$$ExternalSyntheticApiModelOutline0.m(r6, r7, r8)
            throw r6
        Lc3:
            r1 = 0
            kotlin.io.path.PathsKt__PathRecursiveFunctionsKt$copyToRecursively$5 r0 = new kotlin.io.path.PathsKt__PathRecursiveFunctionsKt$copyToRecursively$5
            r0.<init>()
            r3 = r0
            kotlin.jvm.functions.Function1 r3 = (kotlin.jvm.functions.Function1) r3
            r4 = 1
            r5 = 0
            r0 = r6
            r2 = r9
            kotlin.io.path.PathsKt.visitFileTree$default(r0, r1, r2, r3, r4, r5)
            return r7
        Ld4:
            retrofit2.Reflection$Java8$$ExternalSyntheticApiModelOutline0.m2136m()
            java.lang.String r6 = r6.toString()
            java.lang.String r7 = r7.toString()
            java.lang.String r8 = "The source file doesn't exist."
            java.nio.file.NoSuchFileException r6 = retrofit2.Reflection$Java8$$ExternalSyntheticApiModelOutline0.m2119m(r6, r7, r8)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.io.path.PathsKt__PathRecursiveFunctionsKt.copyToRecursively(java.nio.file.Path, java.nio.file.Path, kotlin.jvm.functions.Function3, boolean, kotlin.jvm.functions.Function3):java.nio.file.Path");
    }

    private static final Path copyToRecursively$destination$PathsKt__PathRecursiveFunctionsKt(Path path, Path path2, Path path3) {
        Path resolve;
        resolve = path2.resolve(PathsKt.relativeTo(path3, path).toString());
        Intrinsics.checkNotNullExpressionValue(resolve, "resolve(...)");
        return resolve;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final FileVisitResult copyToRecursively$error$PathsKt__PathRecursiveFunctionsKt(Function3<? super Path, ? super Path, ? super Exception, ? extends OnErrorResult> function3, Path path, Path path2, Path path3, Exception exc) {
        return toFileVisitResult$PathsKt__PathRecursiveFunctionsKt(function3.invoke(path3, copyToRecursively$destination$PathsKt__PathRecursiveFunctionsKt(path, path2, path3), exc));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final FileVisitResult copyToRecursively$copy$PathsKt__PathRecursiveFunctionsKt(Function3<? super CopyActionContext, ? super Path, ? super Path, ? extends CopyActionResult> function3, Path path, Path path2, Function3<? super Path, ? super Path, ? super Exception, ? extends OnErrorResult> function32, Path path3, BasicFileAttributes basicFileAttributes) {
        try {
            return toFileVisitResult$PathsKt__PathRecursiveFunctionsKt(function3.invoke(DefaultCopyActionContext.INSTANCE, path3, copyToRecursively$destination$PathsKt__PathRecursiveFunctionsKt(path, path2, path3)));
        } catch (Exception e) {
            return copyToRecursively$error$PathsKt__PathRecursiveFunctionsKt(function32, path, path2, path3, e);
        }
    }

    private static final FileVisitResult toFileVisitResult$PathsKt__PathRecursiveFunctionsKt(CopyActionResult copyActionResult) {
        FileVisitResult fileVisitResult;
        FileVisitResult fileVisitResult2;
        FileVisitResult fileVisitResult3;
        int i = WhenMappings.$EnumSwitchMapping$0[copyActionResult.ordinal()];
        if (i == 1) {
            fileVisitResult = FileVisitResult.CONTINUE;
            return fileVisitResult;
        } else if (i == 2) {
            fileVisitResult2 = FileVisitResult.TERMINATE;
            return fileVisitResult2;
        } else if (i == 3) {
            fileVisitResult3 = FileVisitResult.SKIP_SUBTREE;
            return fileVisitResult3;
        } else {
            throw new NoWhenBranchMatchedException();
        }
    }

    private static final FileVisitResult toFileVisitResult$PathsKt__PathRecursiveFunctionsKt(OnErrorResult onErrorResult) {
        FileVisitResult fileVisitResult;
        FileVisitResult fileVisitResult2;
        int i = WhenMappings.$EnumSwitchMapping$1[onErrorResult.ordinal()];
        if (i == 1) {
            fileVisitResult = FileVisitResult.TERMINATE;
            return fileVisitResult;
        } else if (i == 2) {
            fileVisitResult2 = FileVisitResult.SKIP_SUBTREE;
            return fileVisitResult2;
        } else {
            throw new NoWhenBranchMatchedException();
        }
    }

    public static final void deleteRecursively(Path path) {
        Intrinsics.checkNotNullParameter(path, "<this>");
        List<Exception> deleteRecursivelyImpl$PathsKt__PathRecursiveFunctionsKt = deleteRecursivelyImpl$PathsKt__PathRecursiveFunctionsKt(path);
        if (!deleteRecursivelyImpl$PathsKt__PathRecursiveFunctionsKt.isEmpty()) {
            FileSystemException m = Trace$$ExternalSyntheticApiModelOutline0.m("Failed to delete one or more files. See suppressed exceptions for details.");
            for (Exception exc : deleteRecursivelyImpl$PathsKt__PathRecursiveFunctionsKt) {
                ExceptionsKt.addSuppressed(m, exc);
            }
            throw m;
        }
    }

    private static final List<Exception> deleteRecursivelyImpl$PathsKt__PathRecursiveFunctionsKt(Path path) {
        Path parent;
        DirectoryStream directoryStream;
        Path fileName;
        boolean z = false;
        boolean z2 = true;
        ExceptionsCollector exceptionsCollector = new ExceptionsCollector(0, 1, null);
        parent = path.getParent();
        if (parent != null) {
            try {
                directoryStream = Files.newDirectoryStream(parent);
            } catch (Throwable unused) {
                directoryStream = null;
            }
            if (directoryStream != null) {
                DirectoryStream directoryStream2 = directoryStream;
                try {
                    DirectoryStream m2115m = Reflection$Java8$$ExternalSyntheticApiModelOutline0.m2115m((Object) directoryStream2);
                    if (Reflection$Java8$$ExternalSyntheticApiModelOutline0.m2138m((Object) m2115m)) {
                        exceptionsCollector.setPath(parent);
                        SecureDirectoryStream m2122m = Reflection$Java8$$ExternalSyntheticApiModelOutline0.m2122m((Object) m2115m);
                        fileName = path.getFileName();
                        Intrinsics.checkNotNullExpressionValue(fileName, "getFileName(...)");
                        handleEntry$PathsKt__PathRecursiveFunctionsKt(m2122m, fileName, exceptionsCollector);
                    } else {
                        z = true;
                    }
                    Unit unit = Unit.INSTANCE;
                    CloseableKt.closeFinally(directoryStream2, null);
                    z2 = z;
                } finally {
                }
            }
        }
        if (z2) {
            insecureHandleEntry$PathsKt__PathRecursiveFunctionsKt(path, exceptionsCollector);
        }
        return exceptionsCollector.getCollectedExceptions();
    }

    private static final void collectIfThrows$PathsKt__PathRecursiveFunctionsKt(ExceptionsCollector exceptionsCollector, Function0<Unit> function0) {
        try {
            function0.invoke();
        } catch (Exception e) {
            exceptionsCollector.collect(e);
        }
    }

    private static final <R> R tryIgnoreNoSuchFileException$PathsKt__PathRecursiveFunctionsKt(Function0<? extends R> function0) {
        try {
            return function0.invoke();
        } catch (NoSuchFileException unused) {
            return null;
        }
    }

    private static final void handleEntry$PathsKt__PathRecursiveFunctionsKt(SecureDirectoryStream<Path> secureDirectoryStream, Path path, ExceptionsCollector exceptionsCollector) {
        LinkOption linkOption;
        exceptionsCollector.enterEntry(path);
        try {
            linkOption = LinkOption.NOFOLLOW_LINKS;
            try {
                if (isDirectory$PathsKt__PathRecursiveFunctionsKt(secureDirectoryStream, path, linkOption)) {
                    int totalExceptions = exceptionsCollector.getTotalExceptions();
                    enterDirectory$PathsKt__PathRecursiveFunctionsKt(secureDirectoryStream, path, exceptionsCollector);
                    if (totalExceptions == exceptionsCollector.getTotalExceptions()) {
                        secureDirectoryStream.deleteDirectory(path);
                        Unit unit = Unit.INSTANCE;
                    }
                } else {
                    secureDirectoryStream.deleteFile(path);
                    Unit unit2 = Unit.INSTANCE;
                }
            } catch (NoSuchFileException unused) {
            }
        } catch (Exception e) {
            exceptionsCollector.collect(e);
        }
        exceptionsCollector.exitEntry(path);
    }

    private static final void enterDirectory$PathsKt__PathRecursiveFunctionsKt(SecureDirectoryStream<Path> secureDirectoryStream, Path path, ExceptionsCollector exceptionsCollector) {
        SecureDirectoryStream secureDirectoryStream2;
        Path fileName;
        LinkOption linkOption;
        try {
            try {
                linkOption = LinkOption.NOFOLLOW_LINKS;
                secureDirectoryStream2 = secureDirectoryStream.newDirectoryStream(path, linkOption);
            } catch (Exception e) {
                exceptionsCollector.collect(e);
                return;
            }
        } catch (NoSuchFileException unused) {
            secureDirectoryStream2 = null;
        }
        if (secureDirectoryStream2 != null) {
            SecureDirectoryStream secureDirectoryStream3 = secureDirectoryStream2;
            SecureDirectoryStream<Object> m2122m = Reflection$Java8$$ExternalSyntheticApiModelOutline0.m2122m((Object) secureDirectoryStream3);
            for (Object obj : m2122m) {
                fileName = Trace$$ExternalSyntheticApiModelOutline0.m343m(obj).getFileName();
                Intrinsics.checkNotNullExpressionValue(fileName, "getFileName(...)");
                handleEntry$PathsKt__PathRecursiveFunctionsKt(m2122m, fileName, exceptionsCollector);
            }
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(secureDirectoryStream3, null);
        }
    }

    private static final boolean isDirectory$PathsKt__PathRecursiveFunctionsKt(SecureDirectoryStream<Path> secureDirectoryStream, Path path, LinkOption... linkOptionArr) {
        Boolean bool;
        FileAttributeView fileAttributeView;
        BasicFileAttributes readAttributes;
        boolean isDirectory;
        try {
            fileAttributeView = secureDirectoryStream.getFileAttributeView(path, Reflection$Java8$$ExternalSyntheticApiModelOutline0.m$1(), (LinkOption[]) Arrays.copyOf(linkOptionArr, linkOptionArr.length));
            readAttributes = Reflection$Java8$$ExternalSyntheticApiModelOutline0.m2125m((Object) fileAttributeView).readAttributes();
            isDirectory = readAttributes.isDirectory();
            bool = Boolean.valueOf(isDirectory);
        } catch (NoSuchFileException unused) {
            bool = null;
        }
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }

    private static final void insecureHandleEntry$PathsKt__PathRecursiveFunctionsKt(Path path, ExceptionsCollector exceptionsCollector) {
        LinkOption linkOption;
        boolean isDirectory;
        try {
            linkOption = LinkOption.NOFOLLOW_LINKS;
            isDirectory = Files.isDirectory(path, (LinkOption[]) Arrays.copyOf(new LinkOption[]{linkOption}, 1));
            if (isDirectory) {
                int totalExceptions = exceptionsCollector.getTotalExceptions();
                insecureEnterDirectory$PathsKt__PathRecursiveFunctionsKt(path, exceptionsCollector);
                if (totalExceptions == exceptionsCollector.getTotalExceptions()) {
                    Files.deleteIfExists(path);
                }
            } else {
                Files.deleteIfExists(path);
            }
        } catch (Exception e) {
            exceptionsCollector.collect(e);
        }
    }

    private static final void insecureEnterDirectory$PathsKt__PathRecursiveFunctionsKt(Path path, ExceptionsCollector exceptionsCollector) {
        DirectoryStream directoryStream;
        try {
            try {
                directoryStream = Files.newDirectoryStream(path);
            } catch (Exception e) {
                exceptionsCollector.collect(e);
                return;
            }
        } catch (NoSuchFileException unused) {
            directoryStream = null;
        }
        if (directoryStream != null) {
            DirectoryStream directoryStream2 = directoryStream;
            for (Object obj : Reflection$Java8$$ExternalSyntheticApiModelOutline0.m2115m((Object) directoryStream2)) {
                Path m343m = Trace$$ExternalSyntheticApiModelOutline0.m343m(obj);
                Intrinsics.checkNotNull(m343m);
                insecureHandleEntry$PathsKt__PathRecursiveFunctionsKt(m343m, exceptionsCollector);
            }
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(directoryStream2, null);
        }
    }
}

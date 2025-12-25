package com.google.common.io;

import androidx.tracing.Trace$$ExternalSyntheticApiModelOutline0;
import com.google.common.base.StandardSystemProperty;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableList;
import com.google.common.io.TempFileCreator;
import j$.util.Objects;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryFlag;
import java.nio.file.attribute.AclEntryType;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermissions;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.EnumSet;
import java.util.Set;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
abstract class TempFileCreator {
    static final TempFileCreator INSTANCE = pickSecureCreator();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract File createTempDir();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract File createTempFile(String prefix) throws IOException;

    private static TempFileCreator pickSecureCreator() {
        try {
            try {
                Class.forName("java.nio.file.Path");
                return new JavaNioCreator();
            } catch (ClassNotFoundException unused) {
                if (((Integer) Class.forName("android.os.Build$VERSION").getField("SDK_INT").get(null)).intValue() < ((Integer) Class.forName("android.os.Build$VERSION_CODES").getField("JELLY_BEAN").get(null)).intValue()) {
                    return new ThrowingCreator();
                }
                return new JavaIoCreator();
            }
        } catch (ClassNotFoundException unused2) {
            return new ThrowingCreator();
        } catch (IllegalAccessException unused3) {
            return new ThrowingCreator();
        } catch (NoSuchFieldException unused4) {
            return new ThrowingCreator();
        }
    }

    static void testMakingUserPermissionsFromScratch() throws IOException {
        JavaNioCreator.access$300().get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static final class JavaNioCreator extends TempFileCreator {
        private static final PermissionSupplier directoryPermissions;
        private static final PermissionSupplier filePermissions;

        /* JADX INFO: Access modifiers changed from: private */
        /* loaded from: classes3.dex */
        public interface PermissionSupplier {
            FileAttribute<?> get() throws IOException;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static /* synthetic */ FileAttribute lambda$userPermissions$3(FileAttribute fileAttribute) throws IOException {
            return fileAttribute;
        }

        private JavaNioCreator() {
            super();
        }

        static /* synthetic */ PermissionSupplier access$300() {
            return userPermissions();
        }

        @Override // com.google.common.io.TempFileCreator
        File createTempDir() {
            Path path;
            Path createTempDirectory;
            File file;
            try {
                path = Paths.get(StandardSystemProperty.JAVA_IO_TMPDIR.value(), new String[0]);
                createTempDirectory = java.nio.file.Files.createTempDirectory(path, null, directoryPermissions.get());
                file = createTempDirectory.toFile();
                return file;
            } catch (IOException e) {
                throw new IllegalStateException("Failed to create directory", e);
            }
        }

        @Override // com.google.common.io.TempFileCreator
        File createTempFile(String prefix) throws IOException {
            Path path;
            Path createTempFile;
            File file;
            path = Paths.get(StandardSystemProperty.JAVA_IO_TMPDIR.value(), new String[0]);
            createTempFile = java.nio.file.Files.createTempFile(path, prefix, null, filePermissions.get());
            file = createTempFile.toFile();
            return file;
        }

        static {
            FileSystem fileSystem;
            Set supportedFileAttributeViews;
            fileSystem = FileSystems.getDefault();
            supportedFileAttributeViews = fileSystem.supportedFileAttributeViews();
            if (supportedFileAttributeViews.contains("posix")) {
                filePermissions = new PermissionSupplier() { // from class: com.google.common.io.TempFileCreator$JavaNioCreator$$ExternalSyntheticLambda20
                    @Override // com.google.common.io.TempFileCreator.JavaNioCreator.PermissionSupplier
                    public final FileAttribute get() {
                        return TempFileCreator.JavaNioCreator.lambda$static$0();
                    }
                };
                directoryPermissions = new PermissionSupplier() { // from class: com.google.common.io.TempFileCreator$JavaNioCreator$$ExternalSyntheticLambda21
                    @Override // com.google.common.io.TempFileCreator.JavaNioCreator.PermissionSupplier
                    public final FileAttribute get() {
                        return TempFileCreator.JavaNioCreator.lambda$static$1();
                    }
                };
            } else if (supportedFileAttributeViews.contains("acl")) {
                PermissionSupplier userPermissions = userPermissions();
                directoryPermissions = userPermissions;
                filePermissions = userPermissions;
            } else {
                PermissionSupplier permissionSupplier = new PermissionSupplier() { // from class: com.google.common.io.TempFileCreator$JavaNioCreator$$ExternalSyntheticLambda22
                    @Override // com.google.common.io.TempFileCreator.JavaNioCreator.PermissionSupplier
                    public final FileAttribute get() {
                        return TempFileCreator.JavaNioCreator.lambda$static$2();
                    }
                };
                directoryPermissions = permissionSupplier;
                filePermissions = permissionSupplier;
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static /* synthetic */ FileAttribute lambda$static$0() throws IOException {
            Set fromString;
            FileAttribute asFileAttribute;
            fromString = PosixFilePermissions.fromString("rw-------");
            asFileAttribute = PosixFilePermissions.asFileAttribute(fromString);
            return asFileAttribute;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static /* synthetic */ FileAttribute lambda$static$1() throws IOException {
            Set fromString;
            FileAttribute asFileAttribute;
            fromString = PosixFilePermissions.fromString("rwx------");
            asFileAttribute = PosixFilePermissions.asFileAttribute(fromString);
            return asFileAttribute;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static /* synthetic */ FileAttribute lambda$static$2() throws IOException {
            FileSystem fileSystem;
            StringBuilder sb = new StringBuilder("unrecognized FileSystem type ");
            fileSystem = FileSystems.getDefault();
            sb.append(fileSystem);
            throw new IOException(sb.toString());
        }

        private static PermissionSupplier userPermissions() {
            FileSystem fileSystem;
            UserPrincipalLookupService userPrincipalLookupService;
            UserPrincipal lookupPrincipalByName;
            AclEntry.Builder newBuilder;
            AclEntryType aclEntryType;
            AclEntry.Builder type;
            AclEntry.Builder principal;
            AclEntry.Builder permissions;
            AclEntryFlag aclEntryFlag;
            AclEntryFlag aclEntryFlag2;
            AclEntry.Builder flags;
            AclEntry build;
            try {
                fileSystem = FileSystems.getDefault();
                userPrincipalLookupService = fileSystem.getUserPrincipalLookupService();
                lookupPrincipalByName = userPrincipalLookupService.lookupPrincipalByName(getUsername());
                newBuilder = AclEntry.newBuilder();
                aclEntryType = AclEntryType.ALLOW;
                type = newBuilder.setType(aclEntryType);
                principal = type.setPrincipal(lookupPrincipalByName);
                permissions = principal.setPermissions(EnumSet.allOf(Trace$$ExternalSyntheticApiModelOutline0.m333m()));
                aclEntryFlag = AclEntryFlag.DIRECTORY_INHERIT;
                aclEntryFlag2 = AclEntryFlag.FILE_INHERIT;
                flags = permissions.setFlags(aclEntryFlag, aclEntryFlag2);
                build = flags.build();
                final ImmutableList of = ImmutableList.of(build);
                final FileAttribute<ImmutableList<AclEntry>> fileAttribute = new FileAttribute<ImmutableList<AclEntry>>() { // from class: com.google.common.io.TempFileCreator.JavaNioCreator.1
                    @Override // java.nio.file.attribute.FileAttribute
                    public String name() {
                        return "acl:acl";
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // java.nio.file.attribute.FileAttribute
                    public ImmutableList<AclEntry> value() {
                        return ImmutableList.this;
                    }
                };
                return new PermissionSupplier() { // from class: com.google.common.io.TempFileCreator$JavaNioCreator$$ExternalSyntheticLambda23
                    @Override // com.google.common.io.TempFileCreator.JavaNioCreator.PermissionSupplier
                    public final FileAttribute get() {
                        return TempFileCreator.JavaNioCreator.lambda$userPermissions$3(fileAttribute);
                    }
                };
            } catch (IOException e) {
                return new PermissionSupplier() { // from class: com.google.common.io.TempFileCreator$JavaNioCreator$$ExternalSyntheticLambda24
                    @Override // com.google.common.io.TempFileCreator.JavaNioCreator.PermissionSupplier
                    public final FileAttribute get() {
                        return TempFileCreator.JavaNioCreator.lambda$userPermissions$4(e);
                    }
                };
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        public static /* synthetic */ FileAttribute lambda$userPermissions$4(IOException iOException) throws IOException {
            throw new IOException("Could not find user", iOException);
        }

        private static String getUsername() {
            String str = (String) Objects.requireNonNull(StandardSystemProperty.USER_NAME.value());
            try {
                Class<?> cls = Class.forName("java.lang.ProcessHandle");
                Class<?> cls2 = Class.forName("java.lang.ProcessHandle$Info");
                Class<?> cls3 = Class.forName("java.util.Optional");
                Method method = cls.getMethod("current", new Class[0]);
                Method method2 = cls.getMethod("info", new Class[0]);
                return (String) Objects.requireNonNull(cls3.getMethod("orElse", Object.class).invoke(cls2.getMethod("user", new Class[0]).invoke(method2.invoke(method.invoke(null, new Object[0]), new Object[0]), new Object[0]), str));
            } catch (ClassNotFoundException unused) {
                return str;
            } catch (IllegalAccessException | NoSuchMethodException unused2) {
                return str;
            } catch (InvocationTargetException e) {
                Throwables.throwIfUnchecked(e.getCause());
                return str;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static final class JavaIoCreator extends TempFileCreator {
        private static final int TEMP_DIR_ATTEMPTS = 10000;

        private JavaIoCreator() {
            super();
        }

        @Override // com.google.common.io.TempFileCreator
        File createTempDir() {
            File file = new File(StandardSystemProperty.JAVA_IO_TMPDIR.value());
            String str = System.currentTimeMillis() + "-";
            for (int i = 0; i < 10000; i++) {
                File file2 = new File(file, str + i);
                if (file2.mkdir()) {
                    return file2;
                }
            }
            throw new IllegalStateException("Failed to create directory within 10000 attempts (tried " + str + "0 to " + str + "9999)");
        }

        @Override // com.google.common.io.TempFileCreator
        File createTempFile(String prefix) throws IOException {
            return File.createTempFile(prefix, null, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static final class ThrowingCreator extends TempFileCreator {
        private static final String MESSAGE = "Guava cannot securely create temporary files or directories under SDK versions before Jelly Bean. You can create one yourself, either in the insecure default directory or in a more secure directory, such as context.getCacheDir(). For more information, see the Javadoc for Files.createTempDir().";

        private ThrowingCreator() {
            super();
        }

        @Override // com.google.common.io.TempFileCreator
        File createTempDir() {
            throw new IllegalStateException(MESSAGE);
        }

        @Override // com.google.common.io.TempFileCreator
        File createTempFile(String prefix) throws IOException {
            throw new IOException(MESSAGE);
        }
    }

    private TempFileCreator() {
    }
}

package com.google.common.base;

import java.io.Serializable;
import javax.annotation.CheckForNull;
@ElementTypesAreNonnullByDefault
/* loaded from: classes3.dex */
public enum CaseFormat {
    LOWER_HYPHEN(CharMatcher.is('-'), "-") { // from class: com.google.common.base.CaseFormat.1
        @Override // com.google.common.base.CaseFormat
        String normalizeWord(String word) {
            return Ascii.toLowerCase(word);
        }

        @Override // com.google.common.base.CaseFormat
        String convert(CaseFormat format, String s) {
            if (format == LOWER_UNDERSCORE) {
                return s.replace('-', '_');
            }
            if (format == UPPER_UNDERSCORE) {
                return Ascii.toUpperCase(s.replace('-', '_'));
            }
            return super.convert(format, s);
        }
    },
    LOWER_UNDERSCORE(CharMatcher.is('_'), "_") { // from class: com.google.common.base.CaseFormat.2
        @Override // com.google.common.base.CaseFormat
        String normalizeWord(String word) {
            return Ascii.toLowerCase(word);
        }

        @Override // com.google.common.base.CaseFormat
        String convert(CaseFormat format, String s) {
            if (format == LOWER_HYPHEN) {
                return s.replace('_', '-');
            }
            if (format == UPPER_UNDERSCORE) {
                return Ascii.toUpperCase(s);
            }
            return super.convert(format, s);
        }
    },
    LOWER_CAMEL(CharMatcher.inRange('A', 'Z'), "") { // from class: com.google.common.base.CaseFormat.3
        @Override // com.google.common.base.CaseFormat
        String normalizeWord(String word) {
            return CaseFormat.firstCharOnlyToUpper(word);
        }

        @Override // com.google.common.base.CaseFormat
        String normalizeFirstWord(String word) {
            return Ascii.toLowerCase(word);
        }
    },
    UPPER_CAMEL(CharMatcher.inRange('A', 'Z'), "") { // from class: com.google.common.base.CaseFormat.4
        @Override // com.google.common.base.CaseFormat
        String normalizeWord(String word) {
            return CaseFormat.firstCharOnlyToUpper(word);
        }
    },
    UPPER_UNDERSCORE(CharMatcher.is('_'), "_") { // from class: com.google.common.base.CaseFormat.5
        @Override // com.google.common.base.CaseFormat
        String normalizeWord(String word) {
            return Ascii.toUpperCase(word);
        }

        @Override // com.google.common.base.CaseFormat
        String convert(CaseFormat format, String s) {
            if (format == LOWER_HYPHEN) {
                return Ascii.toLowerCase(s.replace('_', '-'));
            }
            if (format == LOWER_UNDERSCORE) {
                return Ascii.toLowerCase(s);
            }
            return super.convert(format, s);
        }
    };
    
    private final CharMatcher wordBoundary;
    private final String wordSeparator;

    abstract String normalizeWord(String word);

    CaseFormat(CharMatcher wordBoundary, String wordSeparator) {
        this.wordBoundary = wordBoundary;
        this.wordSeparator = wordSeparator;
    }

    public final String to(CaseFormat format, String str) {
        Preconditions.checkNotNull(format);
        Preconditions.checkNotNull(str);
        return format == this ? str : convert(format, str);
    }

    String convert(CaseFormat format, String s) {
        StringBuilder sb = null;
        int i = 0;
        int i2 = -1;
        while (true) {
            i2 = this.wordBoundary.indexIn(s, i2 + 1);
            if (i2 == -1) {
                break;
            }
            if (i == 0) {
                sb = new StringBuilder(s.length() + (format.wordSeparator.length() * 4));
                sb.append(format.normalizeFirstWord(s.substring(i, i2)));
            } else {
                ((StringBuilder) j$.util.Objects.requireNonNull(sb)).append(format.normalizeWord(s.substring(i, i2)));
            }
            sb.append(format.wordSeparator);
            i = this.wordSeparator.length() + i2;
        }
        if (i == 0) {
            return format.normalizeFirstWord(s);
        }
        StringBuilder sb2 = (StringBuilder) j$.util.Objects.requireNonNull(sb);
        sb2.append(format.normalizeWord(s.substring(i)));
        return sb2.toString();
    }

    public Converter<String, String> converterTo(CaseFormat targetFormat) {
        return new StringConverter(this, targetFormat);
    }

    /* loaded from: classes3.dex */
    private static final class StringConverter extends Converter<String, String> implements Serializable {
        private static final long serialVersionUID = 0;
        private final CaseFormat sourceFormat;
        private final CaseFormat targetFormat;

        StringConverter(CaseFormat sourceFormat, CaseFormat targetFormat) {
            this.sourceFormat = (CaseFormat) Preconditions.checkNotNull(sourceFormat);
            this.targetFormat = (CaseFormat) Preconditions.checkNotNull(targetFormat);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.common.base.Converter
        public String doForward(String s) {
            return this.sourceFormat.to(this.targetFormat, s);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.common.base.Converter
        public String doBackward(String s) {
            return this.targetFormat.to(this.sourceFormat, s);
        }

        @Override // com.google.common.base.Converter, com.google.common.base.Function
        public boolean equals(@CheckForNull Object object) {
            if (object instanceof StringConverter) {
                StringConverter stringConverter = (StringConverter) object;
                return this.sourceFormat.equals(stringConverter.sourceFormat) && this.targetFormat.equals(stringConverter.targetFormat);
            }
            return false;
        }

        public int hashCode() {
            return this.sourceFormat.hashCode() ^ this.targetFormat.hashCode();
        }

        public String toString() {
            return this.sourceFormat + ".converterTo(" + this.targetFormat + ")";
        }
    }

    String normalizeFirstWord(String word) {
        return normalizeWord(word);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String firstCharOnlyToUpper(String word) {
        if (word.isEmpty()) {
            return word;
        }
        return Ascii.toUpperCase(word.charAt(0)) + Ascii.toLowerCase(word.substring(1));
    }
}

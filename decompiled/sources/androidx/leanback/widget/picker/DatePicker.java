package androidx.leanback.widget.picker;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import androidx.leanback.R;
import androidx.leanback.widget.picker.PickerUtility;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
/* loaded from: classes.dex */
public class DatePicker extends Picker {
    private static final int[] DATE_FIELDS = {5, 2, 1};
    static final String DATE_FORMAT = "MM/dd/yyyy";
    static final String LOG_TAG = "DatePicker";
    int mColDayIndex;
    int mColMonthIndex;
    int mColYearIndex;
    PickerUtility.DateConstant mConstant;
    Calendar mCurrentDate;
    final DateFormat mDateFormat;
    private String mDatePickerFormat;
    PickerColumn mDayColumn;
    Calendar mMaxDate;
    Calendar mMinDate;
    PickerColumn mMonthColumn;
    Calendar mTempDate;
    PickerColumn mYearColumn;

    public DatePicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DatePicker(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDateFormat = new SimpleDateFormat(DATE_FORMAT);
        updateCurrentLocale();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.lbDatePicker);
        String string = obtainStyledAttributes.getString(R.styleable.lbDatePicker_android_minDate);
        String string2 = obtainStyledAttributes.getString(R.styleable.lbDatePicker_android_maxDate);
        this.mTempDate.clear();
        if (!TextUtils.isEmpty(string)) {
            if (!parseDate(string, this.mTempDate)) {
                this.mTempDate.set(1900, 0, 1);
            }
        } else {
            this.mTempDate.set(1900, 0, 1);
        }
        this.mMinDate.setTimeInMillis(this.mTempDate.getTimeInMillis());
        this.mTempDate.clear();
        if (!TextUtils.isEmpty(string2)) {
            if (!parseDate(string2, this.mTempDate)) {
                this.mTempDate.set(2100, 0, 1);
            }
        } else {
            this.mTempDate.set(2100, 0, 1);
        }
        this.mMaxDate.setTimeInMillis(this.mTempDate.getTimeInMillis());
        String string3 = obtainStyledAttributes.getString(R.styleable.lbDatePicker_datePickerFormat);
        setDatePickerFormat(TextUtils.isEmpty(string3) ? new String(android.text.format.DateFormat.getDateFormatOrder(context)) : string3);
    }

    private boolean parseDate(String str, Calendar calendar) {
        try {
            calendar.setTime(this.mDateFormat.parse(str));
            return true;
        } catch (ParseException unused) {
            Log.w(LOG_TAG, "Date: " + str + " not in format: MM/dd/yyyy");
            return false;
        }
    }

    String getBestYearMonthDayPattern(String str) {
        String localizedPattern;
        if (PickerUtility.SUPPORTS_BEST_DATE_TIME_PATTERN) {
            localizedPattern = android.text.format.DateFormat.getBestDateTimePattern(this.mConstant.locale, str);
        } else {
            DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getContext());
            localizedPattern = dateFormat instanceof SimpleDateFormat ? ((SimpleDateFormat) dateFormat).toLocalizedPattern() : DATE_FORMAT;
        }
        return TextUtils.isEmpty(localizedPattern) ? DATE_FORMAT : localizedPattern;
    }

    List<CharSequence> extractSeparators() {
        String bestYearMonthDayPattern = getBestYearMonthDayPattern(this.mDatePickerFormat);
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        char[] cArr = {'Y', 'y', 'M', 'm', 'D', 'd'};
        boolean z = false;
        char c = 0;
        for (int i = 0; i < bestYearMonthDayPattern.length(); i++) {
            char charAt = bestYearMonthDayPattern.charAt(i);
            if (charAt != ' ') {
                if (charAt != '\'') {
                    if (z) {
                        sb.append(charAt);
                    } else if (!isAnyOf(charAt, cArr)) {
                        sb.append(charAt);
                    } else if (charAt != c) {
                        arrayList.add(sb.toString());
                        sb.setLength(0);
                    }
                    c = charAt;
                } else if (z) {
                    z = false;
                } else {
                    sb.setLength(0);
                    z = true;
                }
            }
        }
        arrayList.add(sb.toString());
        return arrayList;
    }

    private static boolean isAnyOf(char c, char[] cArr) {
        for (char c2 : cArr) {
            if (c == c2) {
                return true;
            }
        }
        return false;
    }

    public void setDatePickerFormat(String str) {
        if (TextUtils.isEmpty(str)) {
            str = new String(android.text.format.DateFormat.getDateFormatOrder(getContext()));
        }
        if (TextUtils.equals(this.mDatePickerFormat, str)) {
            return;
        }
        this.mDatePickerFormat = str;
        List<CharSequence> extractSeparators = extractSeparators();
        if (extractSeparators.size() != str.length() + 1) {
            throw new IllegalStateException("Separators size: " + extractSeparators.size() + " must equal the size of datePickerFormat: " + str.length() + " + 1");
        }
        setSeparators(extractSeparators);
        this.mDayColumn = null;
        this.mMonthColumn = null;
        this.mYearColumn = null;
        this.mColMonthIndex = -1;
        this.mColDayIndex = -1;
        this.mColYearIndex = -1;
        String upperCase = str.toUpperCase();
        ArrayList arrayList = new ArrayList(3);
        for (int i = 0; i < upperCase.length(); i++) {
            char charAt = upperCase.charAt(i);
            if (charAt != 'D') {
                if (charAt != 'M') {
                    if (charAt == 'Y') {
                        if (this.mYearColumn != null) {
                            throw new IllegalArgumentException("datePicker format error");
                        }
                        PickerColumn pickerColumn = new PickerColumn();
                        this.mYearColumn = pickerColumn;
                        arrayList.add(pickerColumn);
                        this.mColYearIndex = i;
                        this.mYearColumn.setLabelFormat("%d");
                    } else {
                        throw new IllegalArgumentException("datePicker format error");
                    }
                } else if (this.mMonthColumn != null) {
                    throw new IllegalArgumentException("datePicker format error");
                } else {
                    PickerColumn pickerColumn2 = new PickerColumn();
                    this.mMonthColumn = pickerColumn2;
                    arrayList.add(pickerColumn2);
                    this.mMonthColumn.setStaticLabels(this.mConstant.months);
                    this.mColMonthIndex = i;
                }
            } else if (this.mDayColumn != null) {
                throw new IllegalArgumentException("datePicker format error");
            } else {
                PickerColumn pickerColumn3 = new PickerColumn();
                this.mDayColumn = pickerColumn3;
                arrayList.add(pickerColumn3);
                this.mDayColumn.setLabelFormat("%02d");
                this.mColDayIndex = i;
            }
        }
        setColumns(arrayList);
        updateSpinners(false);
    }

    public String getDatePickerFormat() {
        return this.mDatePickerFormat;
    }

    private void updateCurrentLocale() {
        PickerUtility.DateConstant dateConstantInstance = PickerUtility.getDateConstantInstance(Locale.getDefault(), getContext().getResources());
        this.mConstant = dateConstantInstance;
        this.mTempDate = PickerUtility.getCalendarForLocale(this.mTempDate, dateConstantInstance.locale);
        this.mMinDate = PickerUtility.getCalendarForLocale(this.mMinDate, this.mConstant.locale);
        this.mMaxDate = PickerUtility.getCalendarForLocale(this.mMaxDate, this.mConstant.locale);
        this.mCurrentDate = PickerUtility.getCalendarForLocale(this.mCurrentDate, this.mConstant.locale);
        PickerColumn pickerColumn = this.mMonthColumn;
        if (pickerColumn != null) {
            pickerColumn.setStaticLabels(this.mConstant.months);
            setColumnAt(this.mColMonthIndex, this.mMonthColumn);
        }
    }

    @Override // androidx.leanback.widget.picker.Picker
    public final void onColumnValueChanged(int i, int i2) {
        this.mTempDate.setTimeInMillis(this.mCurrentDate.getTimeInMillis());
        int currentValue = getColumnAt(i).getCurrentValue();
        if (i == this.mColDayIndex) {
            this.mTempDate.add(5, i2 - currentValue);
        } else if (i == this.mColMonthIndex) {
            this.mTempDate.add(2, i2 - currentValue);
        } else if (i == this.mColYearIndex) {
            this.mTempDate.add(1, i2 - currentValue);
        } else {
            throw new IllegalArgumentException();
        }
        setDate(this.mTempDate.get(1), this.mTempDate.get(2), this.mTempDate.get(5));
        updateSpinners(false);
    }

    public void setMinDate(long j) {
        this.mTempDate.setTimeInMillis(j);
        if (this.mTempDate.get(1) != this.mMinDate.get(1) || this.mTempDate.get(6) == this.mMinDate.get(6)) {
            this.mMinDate.setTimeInMillis(j);
            if (this.mCurrentDate.before(this.mMinDate)) {
                this.mCurrentDate.setTimeInMillis(this.mMinDate.getTimeInMillis());
            }
            updateSpinners(false);
        }
    }

    public long getMinDate() {
        return this.mMinDate.getTimeInMillis();
    }

    public void setMaxDate(long j) {
        this.mTempDate.setTimeInMillis(j);
        if (this.mTempDate.get(1) != this.mMaxDate.get(1) || this.mTempDate.get(6) == this.mMaxDate.get(6)) {
            this.mMaxDate.setTimeInMillis(j);
            if (this.mCurrentDate.after(this.mMaxDate)) {
                this.mCurrentDate.setTimeInMillis(this.mMaxDate.getTimeInMillis());
            }
            updateSpinners(false);
        }
    }

    public long getMaxDate() {
        return this.mMaxDate.getTimeInMillis();
    }

    public long getDate() {
        return this.mCurrentDate.getTimeInMillis();
    }

    private void setDate(int i, int i2, int i3) {
        this.mCurrentDate.set(i, i2, i3);
        if (this.mCurrentDate.before(this.mMinDate)) {
            this.mCurrentDate.setTimeInMillis(this.mMinDate.getTimeInMillis());
        } else if (this.mCurrentDate.after(this.mMaxDate)) {
            this.mCurrentDate.setTimeInMillis(this.mMaxDate.getTimeInMillis());
        }
    }

    public void updateDate(int i, int i2, int i3, boolean z) {
        if (isNewDate(i, i2, i3)) {
            setDate(i, i2, i3);
            updateSpinners(z);
        }
    }

    private boolean isNewDate(int i, int i2, int i3) {
        return (this.mCurrentDate.get(1) == i && this.mCurrentDate.get(2) == i3 && this.mCurrentDate.get(5) == i2) ? false : true;
    }

    private static boolean updateMin(PickerColumn pickerColumn, int i) {
        if (i != pickerColumn.getMinValue()) {
            pickerColumn.setMinValue(i);
            return true;
        }
        return false;
    }

    private static boolean updateMax(PickerColumn pickerColumn, int i) {
        if (i != pickerColumn.getMaxValue()) {
            pickerColumn.setMaxValue(i);
            return true;
        }
        return false;
    }

    void updateSpinnersImpl(boolean z) {
        boolean updateMin;
        boolean updateMax;
        int[] iArr = {this.mColDayIndex, this.mColMonthIndex, this.mColYearIndex};
        boolean z2 = true;
        boolean z3 = true;
        for (int length = DATE_FIELDS.length - 1; length >= 0; length--) {
            int i = iArr[length];
            if (i >= 0) {
                int i2 = DATE_FIELDS[length];
                PickerColumn columnAt = getColumnAt(i);
                if (z2) {
                    updateMin = updateMin(columnAt, this.mMinDate.get(i2));
                } else {
                    updateMin = updateMin(columnAt, this.mCurrentDate.getActualMinimum(i2));
                }
                boolean z4 = updateMin | false;
                if (z3) {
                    updateMax = updateMax(columnAt, this.mMaxDate.get(i2));
                } else {
                    updateMax = updateMax(columnAt, this.mCurrentDate.getActualMaximum(i2));
                }
                boolean z5 = z4 | updateMax;
                z2 &= this.mCurrentDate.get(i2) == this.mMinDate.get(i2);
                z3 &= this.mCurrentDate.get(i2) == this.mMaxDate.get(i2);
                if (z5) {
                    setColumnAt(iArr[length], columnAt);
                }
                setColumnValue(iArr[length], this.mCurrentDate.get(i2), z);
            }
        }
    }

    private void updateSpinners(final boolean z) {
        post(new Runnable() { // from class: androidx.leanback.widget.picker.DatePicker.1
            @Override // java.lang.Runnable
            public void run() {
                DatePicker.this.updateSpinnersImpl(z);
            }
        });
    }
}

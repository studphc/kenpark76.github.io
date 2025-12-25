package androidx.leanback.widget;

import android.database.Cursor;
import android.util.LruCache;
import androidx.leanback.database.CursorMapper;
/* loaded from: classes.dex */
public class CursorObjectAdapter extends ObjectAdapter {
    private static final int CACHE_SIZE = 100;
    private Cursor mCursor;
    private final LruCache<Integer, Object> mItemCache;
    private CursorMapper mMapper;

    @Override // androidx.leanback.widget.ObjectAdapter
    public boolean isImmediateNotifySupported() {
        return true;
    }

    protected void onMapperChanged() {
    }

    public CursorObjectAdapter(PresenterSelector presenterSelector) {
        super(presenterSelector);
        this.mItemCache = new LruCache<>(100);
    }

    public CursorObjectAdapter(Presenter presenter) {
        super(presenter);
        this.mItemCache = new LruCache<>(100);
    }

    public CursorObjectAdapter() {
        this.mItemCache = new LruCache<>(100);
    }

    public void changeCursor(Cursor cursor) {
        Cursor cursor2 = this.mCursor;
        if (cursor == cursor2) {
            return;
        }
        if (cursor2 != null) {
            cursor2.close();
        }
        this.mCursor = cursor;
        this.mItemCache.trimToSize(0);
        onCursorChanged();
    }

    public Cursor swapCursor(Cursor cursor) {
        Cursor cursor2 = this.mCursor;
        if (cursor == cursor2) {
            return cursor2;
        }
        this.mCursor = cursor;
        this.mItemCache.trimToSize(0);
        onCursorChanged();
        return cursor2;
    }

    protected void onCursorChanged() {
        notifyChanged();
    }

    public final Cursor getCursor() {
        return this.mCursor;
    }

    public final void setMapper(CursorMapper cursorMapper) {
        boolean z = this.mMapper != cursorMapper;
        this.mMapper = cursorMapper;
        if (z) {
            onMapperChanged();
        }
    }

    public final CursorMapper getMapper() {
        return this.mMapper;
    }

    @Override // androidx.leanback.widget.ObjectAdapter
    public int size() {
        Cursor cursor = this.mCursor;
        if (cursor == null) {
            return 0;
        }
        return cursor.getCount();
    }

    @Override // androidx.leanback.widget.ObjectAdapter
    public Object get(int i) {
        Cursor cursor = this.mCursor;
        if (cursor == null) {
            return null;
        }
        if (!cursor.moveToPosition(i)) {
            throw new ArrayIndexOutOfBoundsException();
        }
        Object obj = this.mItemCache.get(Integer.valueOf(i));
        if (obj != null) {
            return obj;
        }
        Object convert = this.mMapper.convert(this.mCursor);
        this.mItemCache.put(Integer.valueOf(i), convert);
        return convert;
    }

    public void close() {
        Cursor cursor = this.mCursor;
        if (cursor != null) {
            cursor.close();
            this.mCursor = null;
        }
    }

    public boolean isClosed() {
        Cursor cursor = this.mCursor;
        return cursor == null || cursor.isClosed();
    }

    protected final void invalidateCache(int i) {
        this.mItemCache.remove(Integer.valueOf(i));
    }

    protected final void invalidateCache(int i, int i2) {
        int i3 = i2 + i;
        while (i < i3) {
            invalidateCache(i);
            i++;
        }
    }
}

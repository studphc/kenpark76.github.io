package androidx.leanback.app;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.speech.SpeechRecognizer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.CompletionInfo;
import android.widget.FrameLayout;
import androidx.leanback.R;
import androidx.leanback.widget.ObjectAdapter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;
import androidx.leanback.widget.SearchBar;
import androidx.leanback.widget.SearchOrbView;
import androidx.leanback.widget.SpeechRecognitionCallback;
import androidx.leanback.widget.VerticalGridView;
import java.util.ArrayList;
import java.util.List;
@Deprecated
/* loaded from: classes.dex */
public class SearchFragment extends Fragment {
    private static final String ARG_PREFIX = "androidx.leanback.app.SearchFragment";
    private static final String ARG_QUERY;
    private static final String ARG_TITLE;
    static final int AUDIO_PERMISSION_REQUEST_CODE = 0;
    static final boolean DEBUG = false;
    private static final String EXTRA_LEANBACK_BADGE_PRESENT = "LEANBACK_BADGE_PRESENT";
    static final int QUERY_COMPLETE = 2;
    static final int RESULTS_CHANGED = 1;
    static final long SPEECH_RECOGNITION_DELAY_MS = 300;
    static final String TAG = "SearchFragment";
    private Drawable mBadgeDrawable;
    private ExternalQuery mExternalQuery;
    private boolean mIsPaused;
    private OnItemViewClickedListener mOnItemViewClickedListener;
    OnItemViewSelectedListener mOnItemViewSelectedListener;
    private boolean mPendingStartRecognitionWhenPaused;
    SearchResultProvider mProvider;
    ObjectAdapter mResultAdapter;
    RowsFragment mRowsFragment;
    SearchBar mSearchBar;
    private SpeechRecognitionCallback mSpeechRecognitionCallback;
    private SpeechRecognizer mSpeechRecognizer;
    int mStatus;
    private String mTitle;
    final ObjectAdapter.DataObserver mAdapterObserver = new ObjectAdapter.DataObserver() { // from class: androidx.leanback.app.SearchFragment.1
        @Override // androidx.leanback.widget.ObjectAdapter.DataObserver
        public void onChanged() {
            SearchFragment.this.mHandler.removeCallbacks(SearchFragment.this.mResultsChangedCallback);
            SearchFragment.this.mHandler.post(SearchFragment.this.mResultsChangedCallback);
        }
    };
    final Handler mHandler = new Handler();
    final Runnable mResultsChangedCallback = new Runnable() { // from class: androidx.leanback.app.SearchFragment.2
        @Override // java.lang.Runnable
        public void run() {
            if (SearchFragment.this.mRowsFragment != null && SearchFragment.this.mRowsFragment.getAdapter() != SearchFragment.this.mResultAdapter && (SearchFragment.this.mRowsFragment.getAdapter() != null || SearchFragment.this.mResultAdapter.size() != 0)) {
                SearchFragment.this.mRowsFragment.setAdapter(SearchFragment.this.mResultAdapter);
                SearchFragment.this.mRowsFragment.setSelectedPosition(0);
            }
            SearchFragment.this.updateSearchBarVisibility();
            SearchFragment.this.mStatus |= 1;
            if ((SearchFragment.this.mStatus & 2) != 0) {
                SearchFragment.this.updateFocus();
            }
            SearchFragment.this.updateSearchBarNextFocusId();
        }
    };
    private final Runnable mSetSearchResultProvider = new Runnable() { // from class: androidx.leanback.app.SearchFragment.3
        @Override // java.lang.Runnable
        public void run() {
            if (SearchFragment.this.mRowsFragment == null) {
                return;
            }
            ObjectAdapter resultsAdapter = SearchFragment.this.mProvider.getResultsAdapter();
            if (resultsAdapter != SearchFragment.this.mResultAdapter) {
                boolean z = SearchFragment.this.mResultAdapter == null;
                SearchFragment.this.releaseAdapter();
                SearchFragment.this.mResultAdapter = resultsAdapter;
                if (SearchFragment.this.mResultAdapter != null) {
                    SearchFragment.this.mResultAdapter.registerObserver(SearchFragment.this.mAdapterObserver);
                }
                if (!z || (SearchFragment.this.mResultAdapter != null && SearchFragment.this.mResultAdapter.size() != 0)) {
                    SearchFragment.this.mRowsFragment.setAdapter(SearchFragment.this.mResultAdapter);
                }
                SearchFragment.this.executePendingQuery();
            }
            SearchFragment.this.updateSearchBarNextFocusId();
            if (SearchFragment.this.mAutoStartRecognition) {
                SearchFragment.this.mHandler.removeCallbacks(SearchFragment.this.mStartRecognitionRunnable);
                SearchFragment.this.mHandler.postDelayed(SearchFragment.this.mStartRecognitionRunnable, SearchFragment.SPEECH_RECOGNITION_DELAY_MS);
                return;
            }
            SearchFragment.this.updateFocus();
        }
    };
    final Runnable mStartRecognitionRunnable = new Runnable() { // from class: androidx.leanback.app.SearchFragment.4
        @Override // java.lang.Runnable
        public void run() {
            SearchFragment.this.mAutoStartRecognition = false;
            SearchFragment.this.mSearchBar.startRecognition();
        }
    };
    String mPendingQuery = null;
    boolean mAutoStartRecognition = true;
    private SearchBar.SearchBarPermissionListener mPermissionListener = new SearchBar.SearchBarPermissionListener() { // from class: androidx.leanback.app.SearchFragment.5
        @Override // androidx.leanback.widget.SearchBar.SearchBarPermissionListener
        public void requestAudioPermission() {
            PermissionHelper.requestPermissions(SearchFragment.this, new String[]{"android.permission.RECORD_AUDIO"}, 0);
        }
    };

    /* loaded from: classes.dex */
    public interface SearchResultProvider {
        ObjectAdapter getResultsAdapter();

        boolean onQueryTextChange(String str);

        boolean onQueryTextSubmit(String str);
    }

    static {
        String canonicalName = SearchFragment.class.getCanonicalName();
        ARG_QUERY = canonicalName + ".query";
        ARG_TITLE = canonicalName + ".title";
    }

    @Override // android.app.Fragment
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i == 0 && strArr.length > 0 && strArr[0].equals("android.permission.RECORD_AUDIO") && iArr[0] == 0) {
            startRecognition();
        }
    }

    public static Bundle createArgs(Bundle bundle, String str) {
        return createArgs(bundle, str, null);
    }

    public static Bundle createArgs(Bundle bundle, String str, String str2) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        bundle.putString(ARG_QUERY, str);
        bundle.putString(ARG_TITLE, str2);
        return bundle;
    }

    public static SearchFragment newInstance(String str) {
        SearchFragment searchFragment = new SearchFragment();
        searchFragment.setArguments(createArgs(null, str));
        return searchFragment;
    }

    @Override // android.app.Fragment
    public void onCreate(Bundle bundle) {
        if (this.mAutoStartRecognition) {
            this.mAutoStartRecognition = bundle == null;
        }
        super.onCreate(bundle);
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.lb_search_fragment, viewGroup, false);
        SearchBar searchBar = (SearchBar) ((FrameLayout) inflate.findViewById(R.id.lb_search_frame)).findViewById(R.id.lb_search_bar);
        this.mSearchBar = searchBar;
        searchBar.setSearchBarListener(new SearchBar.SearchBarListener() { // from class: androidx.leanback.app.SearchFragment.6
            @Override // androidx.leanback.widget.SearchBar.SearchBarListener
            public void onSearchQueryChange(String str) {
                if (SearchFragment.this.mProvider != null) {
                    SearchFragment.this.retrieveResults(str);
                } else {
                    SearchFragment.this.mPendingQuery = str;
                }
            }

            @Override // androidx.leanback.widget.SearchBar.SearchBarListener
            public void onSearchQuerySubmit(String str) {
                SearchFragment.this.submitQuery(str);
            }

            @Override // androidx.leanback.widget.SearchBar.SearchBarListener
            public void onKeyboardDismiss(String str) {
                SearchFragment.this.queryComplete();
            }
        });
        this.mSearchBar.setSpeechRecognitionCallback(this.mSpeechRecognitionCallback);
        this.mSearchBar.setPermissionListener(this.mPermissionListener);
        applyExternalQuery();
        readArguments(getArguments());
        Drawable drawable = this.mBadgeDrawable;
        if (drawable != null) {
            setBadgeDrawable(drawable);
        }
        String str = this.mTitle;
        if (str != null) {
            setTitle(str);
        }
        if (getChildFragmentManager().findFragmentById(R.id.lb_results_frame) == null) {
            this.mRowsFragment = new RowsFragment();
            getChildFragmentManager().beginTransaction().replace(R.id.lb_results_frame, this.mRowsFragment).commit();
        } else {
            this.mRowsFragment = (RowsFragment) getChildFragmentManager().findFragmentById(R.id.lb_results_frame);
        }
        this.mRowsFragment.setOnItemViewSelectedListener(new OnItemViewSelectedListener() { // from class: androidx.leanback.app.SearchFragment.7
            @Override // androidx.leanback.widget.BaseOnItemViewSelectedListener
            public void onItemSelected(Presenter.ViewHolder viewHolder, Object obj, RowPresenter.ViewHolder viewHolder2, Row row) {
                SearchFragment.this.updateSearchBarVisibility();
                if (SearchFragment.this.mOnItemViewSelectedListener != null) {
                    SearchFragment.this.mOnItemViewSelectedListener.onItemSelected(viewHolder, obj, viewHolder2, row);
                }
            }
        });
        this.mRowsFragment.setOnItemViewClickedListener(this.mOnItemViewClickedListener);
        this.mRowsFragment.setExpand(true);
        if (this.mProvider != null) {
            onSetSearchResultProvider();
        }
        return inflate;
    }

    private void resultsAvailable() {
        if ((this.mStatus & 2) != 0) {
            focusOnResults();
        }
        updateSearchBarNextFocusId();
    }

    @Override // android.app.Fragment
    public void onStart() {
        super.onStart();
        VerticalGridView verticalGridView = this.mRowsFragment.getVerticalGridView();
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.lb_search_browse_rows_align_top);
        verticalGridView.setItemAlignmentOffset(0);
        verticalGridView.setItemAlignmentOffsetPercent(-1.0f);
        verticalGridView.setWindowAlignmentOffset(dimensionPixelSize);
        verticalGridView.setWindowAlignmentOffsetPercent(-1.0f);
        verticalGridView.setWindowAlignment(0);
        verticalGridView.setFocusable(false);
        verticalGridView.setFocusableInTouchMode(false);
    }

    @Override // android.app.Fragment
    public void onResume() {
        super.onResume();
        this.mIsPaused = false;
        if (this.mSpeechRecognitionCallback == null && this.mSpeechRecognizer == null) {
            SpeechRecognizer createSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(FragmentUtil.getContext(this));
            this.mSpeechRecognizer = createSpeechRecognizer;
            this.mSearchBar.setSpeechRecognizer(createSpeechRecognizer);
        }
        if (this.mPendingStartRecognitionWhenPaused) {
            this.mPendingStartRecognitionWhenPaused = false;
            this.mSearchBar.startRecognition();
            return;
        }
        this.mSearchBar.stopRecognition();
    }

    @Override // android.app.Fragment
    public void onPause() {
        releaseRecognizer();
        this.mIsPaused = true;
        super.onPause();
    }

    @Override // android.app.Fragment
    public void onDestroy() {
        releaseAdapter();
        super.onDestroy();
    }

    public RowsFragment getRowsFragment() {
        return this.mRowsFragment;
    }

    private void releaseRecognizer() {
        if (this.mSpeechRecognizer != null) {
            this.mSearchBar.setSpeechRecognizer(null);
            this.mSpeechRecognizer.destroy();
            this.mSpeechRecognizer = null;
        }
    }

    public void startRecognition() {
        if (this.mIsPaused) {
            this.mPendingStartRecognitionWhenPaused = true;
        } else {
            this.mSearchBar.startRecognition();
        }
    }

    public void setSearchResultProvider(SearchResultProvider searchResultProvider) {
        if (this.mProvider != searchResultProvider) {
            this.mProvider = searchResultProvider;
            onSetSearchResultProvider();
        }
    }

    public void setOnItemViewSelectedListener(OnItemViewSelectedListener onItemViewSelectedListener) {
        this.mOnItemViewSelectedListener = onItemViewSelectedListener;
    }

    public void setOnItemViewClickedListener(OnItemViewClickedListener onItemViewClickedListener) {
        if (onItemViewClickedListener != this.mOnItemViewClickedListener) {
            this.mOnItemViewClickedListener = onItemViewClickedListener;
            RowsFragment rowsFragment = this.mRowsFragment;
            if (rowsFragment != null) {
                rowsFragment.setOnItemViewClickedListener(onItemViewClickedListener);
            }
        }
    }

    public void setTitle(String str) {
        this.mTitle = str;
        SearchBar searchBar = this.mSearchBar;
        if (searchBar != null) {
            searchBar.setTitle(str);
        }
    }

    public String getTitle() {
        SearchBar searchBar = this.mSearchBar;
        if (searchBar != null) {
            return searchBar.getTitle();
        }
        return null;
    }

    public void setBadgeDrawable(Drawable drawable) {
        this.mBadgeDrawable = drawable;
        SearchBar searchBar = this.mSearchBar;
        if (searchBar != null) {
            searchBar.setBadgeDrawable(drawable);
        }
    }

    public Drawable getBadgeDrawable() {
        SearchBar searchBar = this.mSearchBar;
        if (searchBar != null) {
            return searchBar.getBadgeDrawable();
        }
        return null;
    }

    public void setSearchAffordanceColors(SearchOrbView.Colors colors) {
        SearchBar searchBar = this.mSearchBar;
        if (searchBar != null) {
            searchBar.setSearchAffordanceColors(colors);
        }
    }

    public void setSearchAffordanceColorsInListening(SearchOrbView.Colors colors) {
        SearchBar searchBar = this.mSearchBar;
        if (searchBar != null) {
            searchBar.setSearchAffordanceColorsInListening(colors);
        }
    }

    public void displayCompletions(List<String> list) {
        this.mSearchBar.displayCompletions(list);
    }

    public void displayCompletions(CompletionInfo[] completionInfoArr) {
        this.mSearchBar.displayCompletions(completionInfoArr);
    }

    @Deprecated
    public void setSpeechRecognitionCallback(SpeechRecognitionCallback speechRecognitionCallback) {
        this.mSpeechRecognitionCallback = speechRecognitionCallback;
        SearchBar searchBar = this.mSearchBar;
        if (searchBar != null) {
            searchBar.setSpeechRecognitionCallback(speechRecognitionCallback);
        }
        if (speechRecognitionCallback != null) {
            releaseRecognizer();
        }
    }

    public void setSearchQuery(String str, boolean z) {
        if (str == null) {
            return;
        }
        this.mExternalQuery = new ExternalQuery(str, z);
        applyExternalQuery();
        if (this.mAutoStartRecognition) {
            this.mAutoStartRecognition = false;
            this.mHandler.removeCallbacks(this.mStartRecognitionRunnable);
        }
    }

    public void setSearchQuery(Intent intent, boolean z) {
        ArrayList<String> stringArrayListExtra = intent.getStringArrayListExtra("android.speech.extra.RESULTS");
        if (stringArrayListExtra == null || stringArrayListExtra.size() <= 0) {
            return;
        }
        setSearchQuery(stringArrayListExtra.get(0), z);
    }

    public Intent getRecognizerIntent() {
        Intent intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
        intent.putExtra("android.speech.extra.PARTIAL_RESULTS", true);
        SearchBar searchBar = this.mSearchBar;
        if (searchBar != null && searchBar.getHint() != null) {
            intent.putExtra("android.speech.extra.PROMPT", this.mSearchBar.getHint());
        }
        intent.putExtra(EXTRA_LEANBACK_BADGE_PRESENT, this.mBadgeDrawable != null);
        return intent;
    }

    void retrieveResults(String str) {
        if (this.mProvider.onQueryTextChange(str)) {
            this.mStatus &= -3;
        }
    }

    void submitQuery(String str) {
        queryComplete();
        SearchResultProvider searchResultProvider = this.mProvider;
        if (searchResultProvider != null) {
            searchResultProvider.onQueryTextSubmit(str);
        }
    }

    void queryComplete() {
        this.mStatus |= 2;
        focusOnResults();
    }

    void updateSearchBarVisibility() {
        ObjectAdapter objectAdapter;
        RowsFragment rowsFragment = this.mRowsFragment;
        this.mSearchBar.setVisibility(((rowsFragment != null ? rowsFragment.getSelectedPosition() : -1) <= 0 || (objectAdapter = this.mResultAdapter) == null || objectAdapter.size() == 0) ? 0 : 8);
    }

    void updateSearchBarNextFocusId() {
        ObjectAdapter objectAdapter;
        RowsFragment rowsFragment;
        if (this.mSearchBar == null || (objectAdapter = this.mResultAdapter) == null) {
            return;
        }
        this.mSearchBar.setNextFocusDownId((objectAdapter.size() == 0 || (rowsFragment = this.mRowsFragment) == null || rowsFragment.getVerticalGridView() == null) ? 0 : this.mRowsFragment.getVerticalGridView().getId());
    }

    void updateFocus() {
        RowsFragment rowsFragment;
        ObjectAdapter objectAdapter = this.mResultAdapter;
        if (objectAdapter != null && objectAdapter.size() > 0 && (rowsFragment = this.mRowsFragment) != null && rowsFragment.getAdapter() == this.mResultAdapter) {
            focusOnResults();
        } else {
            this.mSearchBar.requestFocus();
        }
    }

    private void focusOnResults() {
        RowsFragment rowsFragment = this.mRowsFragment;
        if (rowsFragment == null || rowsFragment.getVerticalGridView() == null || this.mResultAdapter.size() == 0 || !this.mRowsFragment.getVerticalGridView().requestFocus()) {
            return;
        }
        this.mStatus &= -2;
    }

    private void onSetSearchResultProvider() {
        this.mHandler.removeCallbacks(this.mSetSearchResultProvider);
        this.mHandler.post(this.mSetSearchResultProvider);
    }

    void releaseAdapter() {
        ObjectAdapter objectAdapter = this.mResultAdapter;
        if (objectAdapter != null) {
            objectAdapter.unregisterObserver(this.mAdapterObserver);
            this.mResultAdapter = null;
        }
    }

    void executePendingQuery() {
        String str = this.mPendingQuery;
        if (str == null || this.mResultAdapter == null) {
            return;
        }
        this.mPendingQuery = null;
        retrieveResults(str);
    }

    private void applyExternalQuery() {
        SearchBar searchBar;
        ExternalQuery externalQuery = this.mExternalQuery;
        if (externalQuery == null || (searchBar = this.mSearchBar) == null) {
            return;
        }
        searchBar.setSearchQuery(externalQuery.mQuery);
        if (this.mExternalQuery.mSubmit) {
            submitQuery(this.mExternalQuery.mQuery);
        }
        this.mExternalQuery = null;
    }

    private void readArguments(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        String str = ARG_QUERY;
        if (bundle.containsKey(str)) {
            setSearchQuery(bundle.getString(str));
        }
        String str2 = ARG_TITLE;
        if (bundle.containsKey(str2)) {
            setTitle(bundle.getString(str2));
        }
    }

    private void setSearchQuery(String str) {
        this.mSearchBar.setSearchQuery(str);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class ExternalQuery {
        String mQuery;
        boolean mSubmit;

        ExternalQuery(String str, boolean z) {
            this.mQuery = str;
            this.mSubmit = z;
        }
    }
}

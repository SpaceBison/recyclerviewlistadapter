package org.spacebison.recyclerviewlistadapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.google.common.collect.testing.ListTestSuiteBuilder;
import com.google.common.collect.testing.SampleElements;
import com.google.common.collect.testing.TestListGenerator;
import com.google.common.collect.testing.features.CollectionFeature;
import com.google.common.collect.testing.features.CollectionSize;
import com.google.common.collect.testing.features.ListFeature;

import junit.framework.Test;
import junit.framework.TestSuite;

import java.util.List;

public class RecyclerViewListAdapterListTest {
    public static Test suite() {
        return new RecyclerViewListAdapterListTest().allTests();
    }

    public Test allTests() {
        TestSuite suite =
                new TestSuite(RecyclerViewListAdapterListTest.class.getCanonicalName());
        suite.addTest(ListTestSuiteBuilder
                .using(new RecyclerViewAdapterListStringTestListGenerator())
                .named("List test")
                .withFeatures(
                        CollectionSize.ANY,
                        CollectionFeature.GENERAL_PURPOSE,
                        CollectionFeature.ALLOWS_NULL_VALUES,
                        CollectionFeature.ALLOWS_NULL_QUERIES,
                        CollectionFeature.NON_STANDARD_TOSTRING,
                        ListFeature.GENERAL_PURPOSE)
                .createTestSuite());
        return suite;
    }

    private static class RecyclerViewAdapterListStringTestListGenerator implements TestListGenerator<String> {
        @Override
        public List<String> create(Object... elements) {
            List<String> listAdapter = new StringDummyViewHolderRecyclerViewListAdapter();

            for (Object element : elements) {
                listAdapter.add((String) element);
            }

            return listAdapter;
        }

        @Override
        public SampleElements<String> samples() {
            return new SampleElements<>("one", "two", "three", "four", "five");
        }

        @Override
        public String[] createArray(int length) {
            return new String[length];
        }

        @Override
        public Iterable<String> order(List<String> insertionOrder) {
            return insertionOrder;
        }
    }

    private static class StringDummyViewHolderRecyclerViewListAdapter extends RecyclerViewListAdapter<String, DummyViewHolder> {
        public StringDummyViewHolderRecyclerViewListAdapter() {
        }

        @Override
        public DummyViewHolder onCreateBindableViewHolder(ViewGroup parent, int viewType) {
            return new DummyViewHolder(parent);
        }
    }

    private static class DummyViewHolder extends BindableViewHolder<String> {
        /**
         * Constructor.
         *
         * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
         */
        public DummyViewHolder(@NonNull ViewGroup parent) {
            super(parent, 0);
        }

        @Override
        public void bind(String data) {
            // do nothing
        }
    }
}
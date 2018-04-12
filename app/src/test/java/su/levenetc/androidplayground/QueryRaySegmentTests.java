package su.levenetc.androidplayground;

import android.support.annotation.NonNull;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import su.levenetc.androidplayground.queryline.QueryModel;
import su.levenetc.androidplayground.queryline.nodes.AutoCompleteNode;
import su.levenetc.androidplayground.queryline.nodes.Node;
import su.levenetc.androidplayground.queryline.nodes.NodesFactory;

import static org.junit.Assert.assertEquals;
import static su.levenetc.androidplayground.QueryRaySegmentTests.AutoTree.array;
import static su.levenetc.androidplayground.QueryRaySegmentTests.AutoTree.branch;
import static su.levenetc.androidplayground.QueryRaySegmentTests.AutoTree.or;

public class QueryRaySegmentTests {

    @Test
    public void testAutoComplete() {
        //select name,age from user
        final QueryModel queryModel = getQueryModel();

        queryModel.append('h');
        queryModel.tryToComplete();
        queryModel.append('s');
        queryModel.append('a');
        queryModel.tryToComplete();

        final LinkedList<Node> nodes = queryModel.getNodes();
        assertEquals(3, nodes.size());
        assertEquals("hello", nodes.get(0).toString());
        assertEquals("sample", nodes.get(1).toString());
        assertEquals("", nodes.get(2).toString());
    }

    @Test
    public void list() {

        final AutoTree tree =
                or(
                        branch("select")
                                .then(array("name", "age"))
                                .thenAuto("where"),
//								.then(array("users", "students"))
//								.then(optional("limit", integers())),
                        branch("delete from")
//								.then(array("users", "students"))
//								.thenAuto("where")
                );

        if (tree == null) {

        }
    }

    @NonNull
    private QueryModel getQueryModel() {
        return new QueryModel(new NodesFactory() {

            @Override
            public Node next() {
                final AutoCompleteNode result = new AutoCompleteNode(new HashSet<String>() {{
                    add("hello");
                    add("sample");
                }});
                result.setQueryModel(queryModel);
                return result;
            }
        });
    }

    static class AutoTree {

        private List<String> variants;
        private List<AutoTree> next;
        private boolean optional;

        public AutoTree(String... variants) {
            this.variants = Arrays.asList(variants);
        }

        static AutoTree start() {
            return new AutoTree();
        }

        static AutoTree or(AutoTree... branches) {
            final AutoTree next = new AutoTree();
            next.next = Arrays.asList(branches);
            return next;
        }

        static AutoTree branch(String variant) {
            return new AutoTree(variant);
        }

        static AutoTree variant(String variant) {
            return new AutoTree();
        }

        static AutoTree array(String... variant) {
            return new AutoTree(variant);
        }

        static AutoTree optional(String optionalVariant, AutoTree variant) {
            final AutoTree tree = new AutoTree(optionalVariant);
            tree.optional = true;
            tree.next = Arrays.asList(variant);
            return variant;
        }

        static AutoTree integers(int... values) {
            return new AutoTree();
        }

        AutoTree node(String variant) {
            return new AutoTree();
        }

        AutoTree then(String... variant) {
            return new AutoTree();
        }

        AutoTree then(AutoTree variant) {
            next = Arrays.asList(variant);
            return this;
        }

        AutoTree thenAuto(String variant) {
            next = Arrays.asList(new AutoTree(variant));
            return this;
        }

        @Override
        public String toString() {

            String[] lll = new String[variants.size()];
            for (int i = 0; i < variants.size(); i++) {
                lll[i] = variants.get(i);
            }

            return Arrays.toString(lll);
        }
    }

    static class BuildNode {

        Map<String, BuildNode> variants = new HashMap<>();

        public BuildNode(String... endVariants) {

        }

        void addVariant(String variant, BuildNode node) {
            variants.put(variant, node);
        }

        void addSingleVariant(BuildNode node, String... variants) {
            for (String variant : variants) {
                this.variants.put(variant, node);
            }
        }
    }

}

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;

public class Main {
    public static void main(){

    }
    public static class graph {
        ArrayList<ArrayList<Integer>> g;
        ArrayList<ArrayList<Integer>> revg;
        ArrayList<Pair<Integer, Integer>> e;
        Integer[] colored;
        boolean[] u;
        int V;

        graph(int n) {
            g = new ArrayList<>();
            revg = new ArrayList<>();
            colored = new Integer[n];
            u = new boolean[n];
            e = new ArrayList<>();
            V = n;
            for (int i = 0; i < n; i++) {
                g.add(new ArrayList<Integer>());
                revg.add(new ArrayList<Integer>());
            }
        }

        void addEdge(Integer v, Integer w) {
            //System.out.println(e.size());
            e.add(new Pair(v, w));
            g.get(v).add(w);
            revg.get(w).add(v);
        }

        Integer countEdge() {
            return e.size();
        }

        void TopDFS(int v, ArrayList<Integer> T) {
            u[v] = true;
            for (int i = 0; i < g.get(v).size(); i++) {
                if (!u[g.get(v).get(i)])
                    TopDFS(g.get(v).get(i), T);
            }
            T.add(v);
        }

        void colorDFS(Integer color, Integer v) {
            u[v] = true;
            colored[v] = color;
            for (int i = 0; i < revg.get(v).size(); i++) {
                if (!u[revg.get(v).get(i)])
                    colorDFS(color, revg.get(v).get(i));
            }
        }

        HashSet<Pair<Integer, Integer>> topSort() {
            ArrayList<Integer> Top = new ArrayList<>();
            for (int i = 0; i < V; i++) {
                u[i] = false;
            }
            for (int i = 0; i < V; i++) {
                if (!u[i])
                    TopDFS(i, Top);
            }
            for (int i = 0; i < V; i++) {
                u[i] = false;
                colored[i] = 0;
            }
            Integer color = 0;
            HashSet<Pair<Integer, Integer>> ans = new HashSet<Pair<Integer, Integer>>();
            for (int i = Top.size() - 1; i >= 0; i--) {
                //System.out.println(Top.get(i));
                if (!u[Top.get(i)]) {
                    colorDFS(color, Top.get(i));
                    color++;
                }
            }
            //for(int i=0;i<V;i++)
            //  System.out.println(colored[i]);
            //System.out.println(ans.size());
            for (int i = 0; i < e.size(); i++) {
                if (!colored[e.get(i).getKey()].equals(colored[e.get(i).getValue()])) {
                    ans.add(new Pair<Integer, Integer>(colored[e.get(i).getKey()], colored[e.get(i).getValue()]));
                }
            }
            return ans;
        }
    }
}
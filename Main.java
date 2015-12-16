import java.util.*;
import java.io.*;

/**
 * Created by ProDota2 on 16.12.2015.
 **/

public class Main {
    public class Pair < K , V >{
        K key;
        V value;
        Pair(K key , V value){
            this.key = key;
            this.value = value;
        }
        public V getValue() {
            return value;
        }
        public void setValue(V value) {
            this.value = value;
        }
        public K getKey() {
            return key;
        }
        public void setKey(K key) {
            this.key = key;
        }
    }
    static class Reader {
        final private int BUFFER_SIZE = 1 << 16;
        private DataInputStream din;
        private byte [] buffer;
        private int bufferPointer, bytesRead;

        public Reader(InputStream in) {
            din = new DataInputStream(in);
            buffer = new byte [ BUFFER_SIZE ];
            bufferPointer = bytesRead = 0;
        }
        public String nextString(int maxSize) {
            byte[] ch = new byte[maxSize];
            int point =  0;
            try {
                byte c = read();
                while (c == ' ' || c == '\n' || c=='\r')
                    c = read();
                while (c != ' ' && c != '\n' && c!='\r') {
                    ch[point++] = c;
                    c = read();
                }
            } catch (Exception e) {}
            return new String(ch, 0,point);
        }
        public int nextInt() {
            int ret =  0;
            boolean neg;
            try {
                byte c = read();
                while (c <= ' ')
                    c = read();
                neg = c == '-';
                if (neg)
                    c = read();
                do {
                    ret = ret * 10 + c - '0';
                    c = read();
                } while (c > ' ');

                if (neg) return -ret;
            } catch (Exception e) {}
            return ret;
        }
        public long nextLong() {
            long ret =  0;
            boolean neg;
            try {
                byte c = read();
                while (c <= ' ')
                    c = read();
                neg = c == '-';
                if (neg)
                    c = read();
                do {
                    ret = ret * 10 + c - '0';
                    c = read();
                } while (c > ' ');

                if (neg) return -ret;
            } catch (Exception e) {}
            return ret;
        }
        private void fillBuffer() {
            try {
                bytesRead = din.read(buffer, bufferPointer =  0, BUFFER_SIZE);
            } catch (Exception e) {}
            if (bytesRead == -1) buffer[0] = -1;
        }
        private byte read() {
            if (bufferPointer == bytesRead) fillBuffer();
            return buffer[bufferPointer++];
        }
    }

    public class graph {
        ArrayList < ArrayList < Integer > > g;
        ArrayList < ArrayList < Integer > > revg;
        ArrayList < Pair < Integer, Integer > > e;
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
                g.add(new ArrayList < Integer >());
                revg.add(new ArrayList < Integer >());
            }
        }

        void addEdge(Integer v, Integer w) {
            e.add(new Pair < Integer , Integer >(v, w));
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

        HashSet < Pair < Integer, Integer > > topSort() {
            ArrayList < Integer > Top = new ArrayList<>();
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
            HashSet < Pair < Integer, Integer > > ans = new HashSet<>();
            for (int i = Top.size() - 1; i >= 0; i--) {
                if (!u[Top.get(i)]) {
                    colorDFS(color, Top.get(i));
                    color++;
                }
            }
            for (int i = 0; i < e.size(); i++) {
                if (!colored[e.get(i).getKey()].equals(colored[e.get(i).getValue()])) {
                    ans.add(new Pair < Integer, Integer >(colored[e.get(i).getKey()], colored[e.get(i).getValue()]));
                }
            }
            return ans;
        }
    }
}
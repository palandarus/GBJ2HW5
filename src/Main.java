import java.lang.reflect.Array;
import java.util.Arrays;

public class Main {
    static final int SIZE = 10000000;
    static final int HALF = SIZE / 2;
    public static float[] ar1=new float[SIZE];

    public static float[] ar2 = new float[SIZE];

    public static void main(String[] args) throws InterruptedException {
        method1();
        method2();
        System.out.println(Arrays.equals(ar1,ar2));

    }

    public static void method1(){
        System.out.println("Method 1 started");
        int arSize=SIZE;
        float[] array = createArray(arSize);
        long a = System.currentTimeMillis();
        calculate(array,0);
        long b = System.currentTimeMillis();
        System.out.println("Method 1 completed, time = "+(b-a));
        ar1=array;
    }

    public static float[] createArray(int size) {
        float[] array=new float[size];
        for (int i = 0; i < size; i++) {
            array[i]=1;
        }
        return array;
    }

    public static float[] calculate(float[] array, int l) {
        for (int i = 0; i < array.length; i++) {
            array[i] = (float)(array[i] * Math.sin(0.2f + (i+l) / 5) * Math.cos(0.2f + (i+l) / 5) * Math.cos(0.4f + (i+l) / 2));
        }
        return array;
    }

    public static void method2() throws InterruptedException {
        System.out.println("Method 2 started");
        float[] array=new float[SIZE];
        float[] half1 = new float[HALF];
        float[] half2 = new float[HALF];
        array=createArray(SIZE);
        long a=System.currentTimeMillis();
        Thread thread1=new Thread(new Runnable() {
            @Override
            public void run() {
                calculate(half1,0);

            }
        });
        Thread thread2=new Thread(new Runnable() {
            @Override
            public void run() {

                calculate(half2,HALF);

            }
        });
        System.arraycopy(array,0,half1,0,HALF);
        System.arraycopy(array,HALF,half2,0,HALF);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.arraycopy(half1, 0, array, 0, HALF);
        System.arraycopy(half2, 0, array, HALF, HALF);
        long b=System.currentTimeMillis();
        System.out.println("Method 2 completed, time = "+(b-a));
        ar2=array;

    }




}

public class mainapp
{
    public static void main(String[]x) {
        bubble bubble = new bubble();
        int arr[] = new int[5];
        arr[0] = 3;
        arr[1] = 11;
        arr[2] = 7;
        arr[3] = 10;
        arr[4] = 20;
        bubble.sort(arr);
        for(int i = 0; i<arr.length; i++){
            System.out.println(arr[i]);
        }
    
    }
}

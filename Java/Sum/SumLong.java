public class SumLong {

    public static void main(String[] args) {
        long sum = 0;
        for (int i = 0; i < args.length; i++) {
            for (int j = 0; j < args[i].length(); j++) {
                int startIndex = j;
                while (j < args[i].length() && !Character.isWhitespace(args[i].charAt(j))) {
                    j++;
                }
                if (startIndex != j) {
                    sum += Long.parseLong(args[i].substring(startIndex, j));
                }
            }    
        }
        System.out.println(sum);
    }
}
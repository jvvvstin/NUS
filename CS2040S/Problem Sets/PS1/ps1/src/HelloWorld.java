public class HelloWorld {
    public static void main(String[] args) {
        String name = "Justin";
        String favJoke = "any Computer Science jokes";
        String background = "Year 1 CS Student who enjoys gaming and listening to music";
        int p1A = 3125;
        String p1B = "Math.pow(argA, argB) in Java or argA raised to the power of argB";

        String intro = String.format(
                "Hello CS2040S!\n" +
                "My name is %s. My favourite jokes are %s. I am " +
                "currently a %s. The answers to Problems 1.a and 1.b are %d and %s respectively.\n" +
                "I am excited to learn CS2040S!\n" +
                "Thank you!",
                name,
                favJoke,
                background,
                p1A,
                p1B);

        System.out.println(intro);
    }
}

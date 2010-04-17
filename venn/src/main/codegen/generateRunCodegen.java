public class generateRunCodegen
{
    static String[] lower = new String[] { "first", "second", "third", "fourth" };
    static String[] Upper = new String[] { "First", "Second", "Third", "Fourth" };
    static String[] UPPER = new String[] { "FIRST", "SECOND", "THIRD", "FOURTH" };

    public static void main(final String[] args)
    {
        ternary(0, 1, 2);
        ternary(0, 1, 3);
        ternary(0, 2, 3);
        ternary(1, 2, 3);

        for (int i = 0; i < 3; i++)
        {
            for (int j = i+1; j < 4; j++)
            {
                binary(i, j);
            }
        }
    }

    private static void ternary(final int i, final int j, final int k)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("velocity.sh --properties \"");
        sb.append("lower0=" + lower[i]);
        sb.append(",lower1=" + lower[j]);
        sb.append(",lower2=" + lower[k]);
        sb.append(",Upper0=" + Upper[i]);
        sb.append(",Upper1=" + Upper[j]);
        sb.append(",Upper2=" + Upper[k]);
        sb.append(",UPPER0=" + UPPER[i]);
        sb.append(",UPPER1=" + UPPER[j]);
        sb.append(",UPPER2=" + UPPER[k]);
        sb.append("\" --template ternaryView.wm");
        System.out.println(sb.toString());
    }

    private static void binary(final int i, final int j)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("velocity.sh --properties \"");
        sb.append("lower0=" + lower[i]);
        sb.append(",lower1=" + lower[j]);
        sb.append(",Upper0=" + Upper[i]);
        sb.append(",Upper1=" + Upper[j]);
        sb.append(",UPPER0=" + UPPER[i]);
        sb.append(",UPPER1=" + UPPER[j]);
        sb.append("\" --template binaryView.wm");
        System.out.println(sb.toString());
    }
}
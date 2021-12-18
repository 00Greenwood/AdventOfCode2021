package Utility;

public class SnailNumber {
    public SnailNumber parent = null;
    public Integer regular = null;
    public SnailNumber left = null;
    public SnailNumber right = null;

    public SnailNumber(SnailNumber parent, String input) {
        this.parent = parent;
        try {
            regular = Integer.valueOf(input);
            return;
        } catch (NumberFormatException ignored) {
        }
        // Not a value, must be a pair.
        int bracket_count = 0;
        int comma_index = -1;
        for (char ch : input.toCharArray()) {
            comma_index++;
            switch (ch) {
                case '[' -> bracket_count++;
                case ']' -> bracket_count--;
                case ',' -> {
                    if (bracket_count == 1) {
                        left = new SnailNumber(this, input.substring(1, comma_index));
                        right = new SnailNumber(this, input.substring(comma_index + 1, input.length() - 1));
                        return;
                    }
                }
            }
        }
    }

    public SnailNumber(SnailNumber left, SnailNumber right) {
        this.left = left;
        this.left.parent = this;
        this.right = right;
        this.right.parent = this;
    }

    public SnailNumber(SnailNumber parent, Integer value) {
        this.parent = parent;
        this.regular = value;
    }

    private void findAndAddToLeft(Integer regular_on_left) {
        SnailNumber current = this;
        while (current.parent != null && current.equals(current.parent.left)) {
            current = current.parent;
        }
        if (current.parent == null) {
            // Could not find the left.
            return;
        }
        // Find the right most regular number in the left snail number.
        SnailNumber on_left = current.parent.left;
        while (on_left.right != null) {
            on_left = on_left.right;
        }
        on_left.regular += regular_on_left;
    }

    private void findAndAddToRight(Integer regular_on_right) {
        SnailNumber current = this;
        while (current.parent != null && current.equals(current.parent.right)) {
            current = current.parent;
        }
        if (current.parent == null) {
            // Could not find the right.
            return;
        }
        // Find the right most regular number in the right snail number.
        SnailNumber on_right = current.parent.right;
        while (on_right.left != null) {
            on_right = on_right.left;
        }
        on_right.regular += regular_on_right;
    }

    public Boolean reduce(Integer depth) {
        if (depth > 3 && left != null && right != null) {
            // Explode
            findAndAddToLeft(left.regular);
            left = null;
            findAndAddToRight(right.regular);
            right = null;
            regular = 0;
            return true;
        }
        if (regular != null && regular >= 10) {
            // Split
            left = new SnailNumber(this, (int) Math.floor(regular / 2.0));
            right = new SnailNumber(this, (int) Math.ceil(regular / 2.0));
            regular = null;
            return true;
        }
        return (left != null && left.reduce(depth + 1)) || (right != null && right.reduce(depth + 1));
    }

    public void reduce() {
        while (reduce(0)) {
            System.out.println(toString(0));
        }
    }

    public String toString(Integer depth) {
        if (regular != null) {
            return regular.toString();
        }
        String output = depth > 3 ? "{" : "[";
        output += left.toString(depth + 1);
        output += ",";
        output += right.toString(depth + 1);
        output += depth > 3 ? "}" : "]";
        return output;
    }
}

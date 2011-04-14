fj-swing attempts to provide bindings between mutable values and Swing components.  For example:

    final Value<String> text = new Value<String>("Enter a number");
    frame.getContentPane().add(TextFieldW.textField(new JTextField(20)).bind(text).unwrap());

This creates a mutable value, 'text', and binds a JTextField to it.  Whenever the user changes the JTextField, the value in text changes, and vice versa.

    final ValueView<Boolean> isANumber = text.map(new F<String, Boolean>() {
        @Override
        public Boolean f(String s) {
            try {
                Integer.parseInt(s);
                return true;
            } catch (NumberFormatException ignored) {
                return false;
            }
        }
    });

This creates a view of the first value, and that view reads as true when the original value (and hence, the text field) contains a value that can be parsed to an int.

    frame.getContentPane().add(ButtonW.button(new JButton("Well done")).bindVisibility(isANumber).unwrap());

This creates a button that is only visible when isANumber reads as true.  A runnable version of this example can be found in Example1.java
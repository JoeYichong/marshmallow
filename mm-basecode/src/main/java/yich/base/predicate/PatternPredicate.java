package yich.base.predicate;


import yich.base.dbc.Require;
import yich.base.logging.JUL;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

abstract public class PatternPredicate<T> extends AltContentPredicate<T> {
    final private static Logger logger = JUL.getLogger(PatternPredicate.class);

    private List<Pattern> patterns;

    public PatternPredicate(String name) {
        super(name);
        init();
    }

    public PatternPredicate(String name, int level) {
        super(name, level);
        init();
    }

    private void init() {
        this.patterns = new ArrayList<>();
    }

//    @Override
//    protected String defaultContentSelector(Topic topic) {
//        return topic == null ? null : topic.getPostTitle();
//    }

    public List<Pattern> getPatterns() {
        return patterns;
    }

    public PatternPredicate addPattern(Pattern... patterns) {
        Require.argumentNotNullAndNotEmpty(patterns, "Pattern... patterns");
        for (Pattern p : patterns) {
            if (p == null){
                logger.warning("** Attempt to add 'null' value pattern!");
                continue;
            }
            this.patterns.add(p);
        }
        return this;
    }

    public PatternPredicate addPattern(String... regs) {
        Require.argumentNotNullAndNotEmpty(regs, "String... regs");
        for (String regex : regs) {
            if (regex == null || regex.length() == 0) {
                logger.warning("** Attempt to add 'null' value or empty regex!");
                continue;
            }
            try {
                this.patterns.add(Pattern.compile(regex, Pattern.DOTALL));
            } catch (PatternSyntaxException e) {
                logger.log(Level.WARNING, "** Invalid Pattern Syntax: \"" + regex + "\"\n", e);
            }
        }
        return this;
    }

    private String isPatternMatched(T target) {
        String content = getContentSelector().apply(target);
        if (content == null)
            return null;
        if (patterns.size() == 0)
            return "";
        for (Pattern pattern : patterns) {
            if (pattern.matcher(content).find()) {
                return "Pattern '" + pattern.toString() + "' Hit";
            }
        }
        return null;
    }


    @Override
    public Object altTest(T target) {
        return isPatternMatched(target);
    }

}

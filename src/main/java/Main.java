import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    //pass condition
    private static final int ATTR_MATCHES_POINTS = 2;//indicates how many attributes need to pass condition
    private static final int TEXT_MATCHES_POINTS = 3;//indicates how important is equals by text

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Wrong number of arguments, program closes");
            System.exit(0);
        }
        File originFile = new File(args[0]);
        File subjectFile = new File(args[1]);
        String id = args[2];
        Document originalDoc = null;
        Document subjectDoc = null;
        try {
            originalDoc = Jsoup.parse(originFile, "UTF-8");
            subjectDoc = Jsoup.parse(subjectFile, "UTF-8");
        } catch (IOException e) {
            System.out.println("Bad file path, program closes");
            System.exit(0);
        }
        Element originalElement = originalDoc.getElementById(id);
        if (originalElement == null) {
            System.out.println("Can't find " + id + " in original file, program closes");
            System.exit(0);
        }
        Elements allSubjectDocElements = subjectDoc.getElementsByTag(originalElement.tagName());
        Map<Integer, Element> elementMap = new TreeMap<>();
        Map<Element, Boolean> textMatch = new HashMap<>();
        for (Element el : allSubjectDocElements) {
            int count = 0;
            for (Attribute atr : originalElement.attributes()) {
                if (el.attr(atr.getKey()).equals(atr.getValue())) {
                    count++;
                }
            }
            if (el.text().equals(originalElement.text())) {
                count += TEXT_MATCHES_POINTS;
                textMatch.put(el, true);
            } else {
                count -= TEXT_MATCHES_POINTS;
                textMatch.put(el, false);
            }
            elementMap.put(count, el);
        }
        int winner = ((TreeMap<Integer, Element>) elementMap).lastKey();
        if (winner < ATTR_MATCHES_POINTS) {
            System.out.println("Not enough matches found, program closes");
            System.exit(0);
        } else {
            System.out.println("Parsing...");
            System.out.println("Matches score:" + winner);
            System.out.println("1 point for every attr matches, " + (textMatch.get(elementMap.get(winner)) ? ("+" + TEXT_MATCHES_POINTS + " points for text match") : ("-" + TEXT_MATCHES_POINTS + " points for text mismatch")));
        }
        Element result = elementMap.get(winner);
        System.out.println("ParentLine:" + getParentLine(result));
    }

    private static String getParentLine(Element element) {
        String parentLine = "";
        while (!"#root".equals(element.tagName()) && element.parent() != null) {
            Elements children = element.parent().children();
            Elements neighbors = new Elements();
            for (Element el : children) {
                if (el.tagName().equals(element.tagName())) {
                    neighbors.add(el);
                }
            }
            if (neighbors.size() > 1) {
                parentLine = element.tagName() + "[" + (neighbors.indexOf(element) + 1) + "]" + (parentLine.equals("") ? "" : ">") + parentLine;
            } else {
                parentLine = element.tagName() + ">" + parentLine;
            }
            element = element.parent();
        }
        return parentLine;
    }
}

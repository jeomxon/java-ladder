package view;

import java.util.List;
import java.util.Map;

import domain.Ladder;
import domain.Lines;
import domain.Name;
import domain.Names;
import domain.Result;
import domain.Results;

public class OutputView {

    private static final int MAXIMUM_LENGTH_OF_NAME = 5;
    private static final int BOUNDARY_OF_NAME_LENGTH = 4;
    private static final String BLANK = " ";
    private static final String CONNECTION = "-";
    private static final String BAR = "|";
    private static final String NEW_LINE = System.getProperty("line.separator");
    private static final StringBuilder namesOutput = new StringBuilder();
    private static final StringBuilder ladderOutput = new StringBuilder();
    private static final StringBuilder resultOutput = new StringBuilder();

    public static void printLadder(final Names names, final Ladder ladder, final Results results) {
        System.out.println(NEW_LINE + "사다리결과" + NEW_LINE);

        System.out.println(makeNamesOutput(names));
        System.out.print(makeLadderOutput(names, ladder));
        System.out.println(makeResultsOutput(results, names));
    }

    private static StringBuilder makeNamesOutput(final Names names) {
        namesOutput.append(findFirstPlayerName(names))
                .append(BLANK);

        for (int i = 1; i < names.getNames().size(); i++) {
            appendPlayerNames(findLongestLengthOfName(names), names.getNames().get(i));
        }

        return namesOutput;
    }

    private static int findLongestLengthOfName(final Names names) {
        return names.getNames()
                .stream()
                .mapToInt(player -> player.getName().length())
                .max()
                .orElseThrow(IllegalArgumentException::new);
    }

    private static String findFirstPlayerName(final Names names) {
        return names.getNames().get(0).getName();
    }

    private static void appendPlayerNames(final int longestName, final Name name) {
        if (isMaximumLengthOfName(name)) {
            namesOutput.append(BLANK)
                    .append(name.getName());
            return;
        }
        drawPlayerName(longestName, name);
    }

    private static boolean isMaximumLengthOfName(final Name name) {
        return name.getName().length() == MAXIMUM_LENGTH_OF_NAME;
    }

    private static void drawPlayerName(final int longestName, final Name name) {
        int numberOfBlank = longestName - name.getName().length();
        namesOutput.append(LadderSymbol.draw(BLANK, numberOfBlank))
                .append(name.getName())
                .append(BLANK);
    }

    private static StringBuilder makeLadderOutput(final Names names, final Ladder ladder) {
        for (int heightIndex = 0; heightIndex < findLadderHeight(ladder); heightIndex++) {
            drawSpaceAtFirst(findLengthOfFirstPlayerName(names));
            drawInnerLadder(names, ladder, heightIndex);
        }

        return ladderOutput;
    }

    private static int findLadderHeight(final Ladder ladder) {
        return ladder.getHeight().getValue();
    }

    private static int findLengthOfFirstPlayerName(final Names names) {
        return names.getNames().get(0).getName().length();
    }

    private static void drawSpaceAtFirst(final int lengthOfFirstPlayerName) {
        if (lengthOfFirstPlayerName >= BOUNDARY_OF_NAME_LENGTH) {
            ladderOutput.append(BLANK.repeat(BOUNDARY_OF_NAME_LENGTH));
            return;
        }
        ladderOutput.append(BLANK.repeat(lengthOfFirstPlayerName));
    }

    private static void drawInnerLadder(final Names names, final Ladder ladder, final int heightIndex) {
        for (Boolean isExistingConnection : findSelectedLine(ladder.getLines(), heightIndex)) {
            ladderOutput.append(BAR);
            drawExistingConnection(names, isExistingConnection);
        }
        ladderOutput.append(BAR)
                .append(NEW_LINE);
    }

    private static List<Boolean> findSelectedLine(final Lines lines, final int selectedPosition) {
        return lines.getLines().get(selectedPosition).getConnections();
    }

    private static void drawExistingConnection(final Names names, final Boolean isExistingConnection) {
        if (isExistingConnection) {
            ladderOutput.append(CONNECTION.repeat(findLongestLengthOfName(names)));
            return;
        }
        ladderOutput.append(BLANK.repeat(findLongestLengthOfName(names)));
    }

    private static StringBuilder makeResultsOutput(final Results results, final Names names) {
        for (Result result : results.getResults()) {
            resultOutput.append(result.getResult())
                    .append(BLANK.repeat(findLongestLengthOfName(names) - result.getResult().length()));
        }

        return resultOutput;
    }

    public static void printResult(final String inputName, final Map<String, String> gameResult) {
        System.out.println(NEW_LINE + "실행 결과");
        validateExistingName(inputName, gameResult);

        if (inputName.equals("all")) {
            makeAllNameAndResult(gameResult);
            return;
        }
        System.out.println(gameResult.get(inputName));
    }

    private static void validateExistingName(final String name, final Map<String, String> gameResult) {
        if (!(name.equals("all") || gameResult.containsKey(name))) {
            throw new IllegalArgumentException("해당 되는 이름을 가진 사람은 참여하지 않았습니다.");
        }
    }

    private static void makeAllNameAndResult(final Map<String, String> gameResult) {
        for (String name : gameResult.keySet()) {
            System.out.println(name + " : " + gameResult.get(name));
        }
    }
}

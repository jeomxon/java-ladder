package domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GameResultTest {

    private Map<String, String> result;
    private GameResult gameResult;

    @BeforeEach
    void setUp() {
        result = new LinkedHashMap<>();
        result.put("pobi", "꽝");
        result.put("honux", "5000");
        result.put("crong", "꽝");
        result.put("jk", "3000");

        gameResult = new GameResult(result);
    }

    @DisplayName("사다리 게임 결과가 주어지면 GameResult가 생성된다.")
    @Test
    void create_game_result() {
        // when & then
        assertThat(gameResult.getGameResult()).isEqualTo(result);
    }

    @DisplayName("이름이 주어지면 사다리를 타고난 결과를 반환한다.")
    @Test
    void returns_result_when_name_given() {
        // given
        String name = "honux";

        // when
        String result = gameResult.findResult(name);
        
        // then
        assertThat(result).isEqualTo(gameResult.getGameResult().get(name));
    }
}

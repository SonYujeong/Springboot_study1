package com.example.firstproject.ioc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest //IoC 컨테이너를 테스트에서 사용한다.
class ChefTest {

    @Autowired //IoC 컨테이너가 객체를 DI하게 한다.
    IngredientFactory ingredientFactory;

    @Autowired Chef chef;

    @Test
    void 돈가스_요리하기() {
        //준비
        //IngredientFactory ingredientFactory = new IngredientFactory();
        //의존성 주입 DI->동작에 필요한 객체를 외부에서 받아온다.
        //Chef chef = new Chef(ingredientFactory);
        String menu = "돈가스";

        //수행
        String food = chef.cook(menu);

        //예상
        String expected = "한돈 등심으로 만든 돈가스";

        //검증
        assertEquals(expected, food);
        System.out.println(food);
    }

    @Test
    void 스테이크_요리하기() {
        //준비
        //IngredientFactory ingredientFactory = new IngredientFactory();
        //Chef chef = new Chef(ingredientFactory);
        String menu = "스테이크";

        //수행
        String food = chef.cook(menu);

        //예상
        String expected = "한우 꽃등심으로 만든 스테이크";

        //검증
        assertEquals(expected, food);
        System.out.println(food);
    }

    @Test
    void 크리스피_치킨_요리하기() {
        //준비
        //IngredientFactory ingredientFactory = new IngredientFactory();
        //Chef chef = new Chef(ingredientFactory);
        String menu = "크리스피 치킨";

        //수행
        String food = chef.cook(menu);

        //예상
        String expected = "국내산 10호 닭으로 만든 크리스피 치킨";

        //검증
        assertEquals(expected, food);
        System.out.println(food);
    }
}
package com.bianquan.springShop;

import com.bianquan.springShop.common.utils.QWrapper;
import com.bianquan.springShop.modules.admin.entity.MenuEntity;
import com.bianquan.springShop.modules.admin.serivice.MenuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringShopApplicationTests {

	@Autowired
	private MenuService menuService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void	selectCount() {

		QWrapper<MenuEntity> wrapper = new QWrapper<>();
		wrapper.eq(MenuEntity.PARENT_KEY, "1567993150Y7YvA");
		List<MenuEntity> list = menuService.list(wrapper);
		System.out.println(list.size());

		int count = menuService.count(wrapper);
		System.out.println(count);

	}

}

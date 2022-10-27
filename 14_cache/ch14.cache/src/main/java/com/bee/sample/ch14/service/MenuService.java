package com.bee.sample.ch14.service;

import com.bee.sample.ch14.entity.Menu;
import com.bee.sample.ch14.pojo.MenuNode;

public interface MenuService {
    void addMenu(Menu menu);

    Menu getMenu(Long id);

    MenuNode getMenuTree();
}

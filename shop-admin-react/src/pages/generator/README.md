#### 前端代码生成器

代码生成器简化到只能生成列表页和模态框编辑页

###### 说明
代码生成器生成代码只能适配后端基础增删改查，复杂需求需要手动修改生成后的文件，
本次改动：抽取出页面的ajax请求，统一封装到api文件夹中，
代码生成文件包含ModuleList.jsx页面、ModuleEdit.jsx页面和api/module.js三个文件(module为生成的模块名称)


###### 字段说明
1. ajax请求前缀：配合后端代码生成器生成默认增删改查代码，例如menus页面，后端增删改查对应为admin/menus/list,admin/menus/add,admin/menus/edit,admin/menus/del,前端代码生成器顺应次规则，请求前缀只需填写admin/menus即可生成对应请求地址

2. 权限前缀：项目配合后端shiro权限验证，权限key为请求路由中/更换为:，例如在menus页面，请求api的URL为admin/menus/list,则该方法完整权限需求为admin:menus:list，因此前端代码生成器权限前缀为admin:menus

3. 权限码：页面添加的按钮有权限码输入框，如menus页面的添加按钮，权限码填add即可，该按钮生成的权限为admin:menus:add
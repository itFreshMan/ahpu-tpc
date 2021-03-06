Ext.namespace("ST.base");
Ext.reg('genderField', ST.ux.ExtField.ComboBox);
Ext.reg('areaField', ST.ux.ExtField.ComboBox);
ST.base.orgView = Ext.extend(ST.ux.ViewTree, {
	dlgWidth: 300,
	dlgHeight: 250,
	urlSaveOrder: "/org/saveOrgOrder.json",
	urlTreeQuery: "/org/queryOrgs.json",
	urlRemoveNode: "/org/deleteOrg.json",
	urlAddNode: "/org/insertOrg.json",
	urlEditNode: "/org/updateOrg.json",
	urlLoadNode: "/org/loadOrg.json",
	addTitle: "增加机构",
    editTitle: "更新机构",
    treePanelTitle:"机构树",
	girdColumns: [
				{header: 'ID', width: 150, name: 'id', hideForm: 'all'},
	            {header: '机构名称', width: 150, name: 'text', allowBlank:false,maxLength:20,maxLengthText: '长度不超过20'},
	            {header: '所属区域', width: 150,name:'areaCode', hiddenName: 'areaCode', allowBlank:false, dictTypeCode:'CORE.ORG.AREA', fieldtype:'areaField', showAll:false},
	            {header: 'parentId', width: 200, name: 'parentId', fieldtype:'hidden', id: 'Ext-parentId'},
	            {header: 'orgSeq', width: 200, name: 'orgSeq', fieldtype:'hidden'},
	            {header: 'level', width: 200, name: 'level', fieldtype:'hidden', id:'Ext-level'},
	            {header: '顺序', width: 60, sortable: true, name: 'theSort', allowBlank:false,regex:/[0-9]+/,regexText:"请输入数字"},
	            {header: '描述', width: 250, name: 'descn', fieldtype:'textarea'}
	        ],
	        
	displayEast: true,
	eastGridTitle: '选择机构用户',
	urlEastGridQuery: '/org/queryUsers4CascadeOrg.json',
	eastGridColumn: [{header: "用户名称", width: 80, sortable: true, name: 'userName'},
            {header: "登录账号", width: 80, sortable: true, name: 'userCode'},
            {header: "所属机构", width: 80, sortable: true, name: 'orgName'},
            {header: '性别', width: 50, name: 'gender_Name'},
            {header: "电话号码", width: 90, name: 'phoneNo'},
            {header: '移动号码', width: 100, name: 'mPhoneNo'},
            {header: "邮箱", width: 200, sortable: true, name: 'email'}],
	
    setFieldDefaultValue: function(node) {
    	Ext.getCmp("Ext-parentId").setRawValue(node.id);
    	Ext.getCmp("Ext-level").setRawValue(node.attributes.level+1);
    },
    
    /**
     * Center 区域 Tree的行beforedblclick事件调用方法
     */
    _orgId: '',
    beforedblclickFn: function(node, e) {
    	this._orgId = node.id;
		this.east.store.load({
            params:{orgId: node.id, start:0, limit:this.eastPageSize}
        });
    	return false;
    },
    
    beforeloadFn: function(node, e){
    	this.tree.getLoader().baseParams = {'areaCode':node.attributes.areaCode};
    },
    
    addUser: function() {
    	this.addUserFormPanel = new Ext.form.FormPanel({
            defaultType: 'textfield',
            labelAlign: 'right',
            labelWidth: 80,
            frame: true,
            id: "addUserFormPanelID",
            autoScroll: true,
            buttonAlign: 'center',
            url: '/user/insertUser.json',
            items: [
	            {fieldLabel: '用户名称', anchor: '90%', dataIndex: 'userName', name:"userName",allowBlank:false},
	            {fieldLabel: '登录账号', anchor: '90%', dataIndex: 'userCode', name:"userCode",allowBlank:false},
	            {fieldLabel: '所属机构', anchor: '90%', dataIndex: 'orgId', name:"orgId",hideGrid: true, inputType:"hidden", id:"org_Id"},
	            {fieldLabel: '性别', anchor: '90%', dataIndex: 'gender', hiddenName: 'gender',hideGrid: true, dictTypeCode: "CORE.GENDER",allowBlank:false,showAll:false, xtype:'genderField'},
	            {fieldLabel: '性别', anchor: '90%', dataIndex: 'gender_Name', hideForm: 'all',hideGrid: true,inputType:"hidden"},
	            {fieldLabel: '电话号码', anchor: '90%', dataIndex: 'phoneNo',name:"phoneNo"},
	            {fieldLabel: '移动号码', anchor: '90%', dataIndex: 'mPhoneNo',name:"mPhoneNo"},
	            {fieldLabel: '邮箱', anchor: '90%', dataIndex: 'email',name:"email"}
	        ],
            scope: this,
            buttons: [{
                text: '确定',
                scope: this,
                handler: function() {
                    if (this.addUserFormPanel.getForm().isValid()) {
                        this.addUserFormPanel.getForm().submit({
                        	waitMsg : '正在处理，请稍等...',
                            success: function(a, b) {
                                this.addUserDialog.close();
                                this.refresh();
                            },
                            failure: function(a, b) {
                            	console.info(b);
								Ext.MessageBox.alert("提示", b.result.message);
                            },
                            scope: this
                        });
                    }
                }
            },{
                text: '取消',
                scope: this,
                handler: function() {
			    	this.addUserDialog.close();
                }
            }]
        });
        
        this.addUserDialog = new Ext.Window({
            //layout: 'fit',
            title: '添加用户',
            modal: true,
            width: 360,
            height: 300,
            closeAction: 'close',
            items: [this.addUserFormPanel]
        });
        
	   	this.addUserDialog.show();
	   	
	   	var node = this.getSelectedNode();
	   	Ext.getCmp("org_Id").setValue(node.id);
    },
    
    addMenuItem: function(items) {
    	items.push("-");
    	items.push({
            id      : 'addUserId',
            handler : this.addUser.createDelegate(this),
            iconCls : 'add',
            text    : '添加用户'
        });
    },
    
	constructor: function() {
		ST.base.orgView.superclass.constructor.call(this, {});
		this.east.store.on('beforeload', function(thiz,options) { 
			Ext.apply(thiz.baseParams, {orgId: this._orgId}); 
		}, this);
		//分页查询所有用户
		this.east.store.load({
            params:{start:0, limit:this.eastPageSize}
        });
	}
});
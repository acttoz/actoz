/*
    Cornerstone Framework v0.9.2

    COPYRIGHT(C) 2012 BY SKTELECOM CO., LTD. ALL RIGHTS RESERVED.
    Released under the Apache License, Version 2.0
*/
define(["backbone","lawnchair"],function(e,t){var n={};return n.createSync=function(t){return function(n,r,i){switch(n){case"read":r instanceof e.Collection?t.readAll&&t.readAll(r,i):t.read&&t.read(r,i);break;case"create":t.create&&t.create(r,i);break;case"update":t.update&&t.update(r,i);break;case"delete":t["delete"]&&t["delete"](r,i)}}},n.local=n.createSync({readAll:function(e,n){new t({adapter:"dom"},function(){this.all(function(t){var r=[];for(var i=0;i<t.length;i++)t[i].model.collectionUrl===e.url&&r.push(t[i].model);n.success(r)})})},read:function(e,n){new t({adapter:"dom"},function(){this.get(e.id,function(e){e?n.success(e.model):n.error("Record not found")})})},create:function(e,n){new t({adapter:"dom"},function(){e.id||e.set({id:e.cid}),e.collection&&e.set({collectionUrl:e.collection.url}),this.save({key:e.id,model:e.toJSON()},function(t){n.success(e)})})},update:function(e,n){new t({adapter:"dom"},function(){this.save({key:e.id,model:e.toJSON()},function(t){n.success(e)})})},"delete":function(e,n){new t({adapter:"dom"},function(){this.remove(e.id,function(){n.success()})})}}),n})
/*
    Cornerstone Framework v0.9.2

    COPYRIGHT(C) 2012 BY SKTELECOM CO., LTD. ALL RIGHTS RESERVED.
    Released under the Apache License, Version 2.0
*/
define(["backbone","underscore","jquery","validation-view","bootstrap"],function(e,t,n,r){return e.View.extend({initialize:function(){this.render(),this.model.on("change",this.render,this),this.options.validationViewClass?this.validation=new this.options.validationViewClass({el:this.$el}):this.validation=new r({el:this.$el})},render:function(){var e=this;t.each(this.model.attributes,function(n,r){var i=e.$(":input[name="+r+"]:first");if(!i||!i.length)return;var s=i.attr("type");s&&s.toUpperCase()==="CHECKBOX"?(e.$(":input[name="+r+"]").removeAttr("checked"),t.isArray(n)?t.each(n,function(t,n){e.$(":input[name="+r+"][value="+t+"]").attr("checked","checked")}):e.$(":input[name="+r+"][value="+n+"]").attr("checked","checked")):s&&s.toUpperCase()==="RADIO"?e.$(":input[name="+r+"][value="+n+"]").attr("checked","checked"):i.val(n)})},_onValidationError:function(e,n){t.isArray(n)?t.each(n,this.validation.fail):this.validation.fail(n)},toModel:function(){this.validation.reset();var e={};return t.each(this.$el.serializeArray(),function(n,r){e[n.name]?(t.isArray(e[n.name])||(e[n.name]=[e[n.name]]),e[n.name].push(n.value)):e[n.name]=n.value}),this.model.off("error",this._onValidationError,this),this.model.on("error",this._onValidationError,this),this.model.clear({silent:!0}),this.model.set(e),this.model.isValid()&&this.validation.success(),this.model}})})
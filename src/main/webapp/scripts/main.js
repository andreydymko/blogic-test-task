dojo.require("dojo.store.JsonRest");
dojo.require("dojo.store.Observable");
dojo.require("dijit.Tree");
dojo.require("dijit.tree.dndSource");
dojo.ready(function(){
    usGov = dojo.store.JsonRest({
        target:"data/",
        mayHaveChildren: function(object){
            // see if it has a children property
            return "children" in object;
        },
        getChildren: function(object, onComplete, onError){
            // retrieve the full copy of the object
            this.get(object.id).then(function(fullObject){
                // copy to the original object so it has the children array as well.
                object.children = fullObject.children;
                // now that full object, we should have an array of children
                onComplete(fullObject.children);
            }, function(error){
                // an error occurred, log it, and indicate no children
                console.error(error);
                onComplete([]);
            });
        },
        getRoot: function(onItem, onError){
            // get the root object, we will do a get() and callback the result
            this.get("root").then(onItem, onError);
        },
        getLabel: function(object){
            // just get the name
            return object.name;
        },

        pasteItem: function(child, oldParent, newParent, bCopy, insertIndex){
            var store = this;
            store.get(oldParent.id).then(function(oldParent){
                store.get(newParent.id).then(function(newParent){
                    var oldChildren = oldParent.children;
                    dojo.some(oldChildren, function(oldChild, i){
                        if(oldChild.id == child.id){
                            oldChildren.splice(i, 1);
                            return true; // done
                        }
                    });
                    store.put(oldParent);
                    newParent.children.splice(insertIndex || 0, 0, child);
                    store.put(newParent);
                }, function(error){
                    alert("Error occurred (this demo is not hooked up to a real database, so this is expected): " + error);
                });
            });
        },
        put: function(object, options){
            //this.onChange(object);
            this.onChildrenChange(object, object.children);
            this.onChange(object);
            return dojo.store.JsonRest.prototype.put.apply(this, arguments);
        }
    });
    tree = new dijit.Tree({
        model: usGov,
        dndController: dijit.tree.dndSource
    }, "tree"); // make sure you have a target HTML element with this id
    tree.startup();
    dojo.query("#add-new-child").onclick(function(){
        var selectedObject = tree.get("selectedItems")[0];
        if(!selectedObject){
            return alert("No object selected");
        }
        usGov.get(selectedObject.id).then(function(selectedObject){
            selectedObject.children.push({
                name: "New child",
                id: Math.random()
            });
            usGov.put(selectedObject);
        });

    });
    dojo.connect(tree, "onDblClick", function(object){
        object.name = prompt("Enter a new name for the object");
        usGov.put(object);
    });
});
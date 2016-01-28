/**
 * Created by John on 2016/1/9.
 */
var labelType, useGradients, nativeTextSupport, animate;

(function() {
    var ua = navigator.userAgent,
        iStuff = ua.match(/iPhone/i) || ua.match(/iPad/i),
        typeOfCanvas = typeof HTMLCanvasElement,
        nativeCanvasSupport = (typeOfCanvas == 'object' || typeOfCanvas == 'function'),
        textSupport = nativeCanvasSupport
            && (typeof document.createElement('canvas').getContext('2d').fillText == 'function');
    //I'm setting this based on the fact that ExCanvas provides text support for IE
    //and that as of today iPhone/iPad current text support is lame
    labelType = (!nativeCanvasSupport || (textSupport && !iStuff))? 'Native' : 'HTML';
    nativeTextSupport = labelType == 'Native';
    useGradients = nativeCanvasSupport;
    animate = !(iStuff || !nativeCanvasSupport);
})();

var Log = {
    elem: false,
    write: function(text){
        if (!this.elem)
            this.elem = document.getElementById('log');
        this.elem.innerHTML = text;
        this.elem.style.left = (500 - this.elem.offsetWidth / 2) + 'px';
    }
};

function init(){

    //init Spacetree
    //Create a new ST instance
    var st = new $jit.ST({
        //id of viz container element
        orientation: "top",
        injectInto: 'infovis',
        //set duration for the animation
        duration: 800,
        levelsToShow: 2,
        //set animation transition type
        transition: $jit.Trans.Quart.easeInOut,
        //set distance between node and its children
        levelDistance: 50,
        //enable panning
        Navigation: {
            enable:true,
            panning:true
        },
        //set node and edge styles
        //set overridable=true for styling individual
        //nodes or edges
        Node: {
            height: 30,
            width: 50,
            type: 'rectangle',
            color: '#aaa',
            overridable: true
        },

        Edge: {
            type: 'bezier',
            overridable: true
        },
        request: function(nodeId, level, controller) {
            $.ajax({
                type: "GET",
                url: "http://localhost:8080/hrms/identity/sub",
                contentType: "application/json; charset=utf-8",
                data: {id: nodeId, layer: level},
                dataType: "json",
                timeout: "10000",
                success: function (data) {
                    var selectedNodeLayer = getNodeLayer(st.graph, nodeId);
                    if (selectedNodeLayer == -1) { // avoid while request node cannot find and cause a javascript syntax error
                        controller.onComplete();
                        return;
                    }
                    controller.onComplete(nodeId, transform(data, selectedNodeLayer));
                },
                error: function() {
                    controller.onComplete();
                }
            });
        },
        onBeforeCompute: function(node){
            Log.write("loading " + node.name);
        },

        onAfterCompute: function(){
            Log.write("done");
        },
        Events: {
            enable: true,
            onClick: function (node, eventInfo, e) {
                var layer = node.data.layer;
                st.onClick(node.id);
            },
            onRightClick: function(node) {
                //var option = {
                //    animation: true,
                //    placement: "top",
                //    title: "详细信息",
                //    content: "测试"
                //};
                //$("#" + nodeId).popover(option);
            }
        },
        //This method is called on DOM label creation.
        //Use this method to add event handlers and styles to
        //your node.
        onCreateLabel: function(label, node){
            label.id = node.id;
            label.innerHTML = node.name;

            //set label styles
            var style = label.style;
            style.width = node.Config.width + 'px';
            style.height = node.Config.height + 'px';
            style.cursor = 'pointer';
            style.color = '#333';
            style.fontSize = '0.8em';
            style.textAlign= 'center';
            //style.paddingTop = '3px';
        },

        //This method is called right before plotting
        //a node. It's useful for changing an individual node
        //style properties before plotting it.
        //The data properties prefixed with a dollar
        //sign will override the global node style properties.
        onBeforePlotNode: function(node){
            //add some color to the nodes in the path between the
            //root node and the selected node.
            if (node.selected) {
                node.data.$color = "#ff7";
            }
            else {
                delete node.data.$color;
                //if the node belongs to the last plotted level
                if(!node.anySubnode("exist")) {
                    //count children number
                    var count = 0;
                    node.eachSubnode(function(n) { count++; });
                    //assign a node color based on
                    //how many children it has
                    node.data.$color = ['#aaa', '#baa', '#caa', '#daa', '#eaa', '#faa'][count];
                }
            }
        },

        //This method is called right before plotting
        //an edge. It's useful for changing an individual edge
        //style properties before plotting it.
        //Edge data proprties prefixed with a dollar sign will
        //override the Edge global style properties.
        onBeforePlotLine: function(adj){
            if (adj.nodeFrom.selected && adj.nodeTo.selected) {
                adj.data.$color = "#eed";
                adj.data.$lineWidth = 3;
            }
            else {
                delete adj.data.$color;
                delete adj.data.$lineWidth;
            }
        }
    });
    console.log(st); //TODO
    $.ajax({
        url:"http://localhost:8080/hrms/identity/rootTree",
        type: "GET",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        data:{level: st.config.levelsToShow},
        success: function(data) {
            //load json data
            var stJson = transform(data, 1);
            st.loadJSON(stJson);
            //compute node positions and layout
            st.compute();
            //optional: make a translation of the tree
            st.geom.translate(new $jit.Complex(-200, 0), "current");
            //emulate a click on the root node.
            st.onClick(st.root);
            //end
        },
        error: function () {
            // show user a request error hint;
        }
    });

}
/**
 *  get current selected node's depth
 * @param graph
 * @param nodeId
 * @returns {number}
 */
function getNodeLayer(graph, nodeId) {
    if (!graph) return 0;
    var layer = -1;
    graph.eachNode(function(n) {
        if (n.exist && n.id == nodeId) {
            console.log(n);
            layer = n.data.layer;
            return;
        }
    });
    return layer;
}

function transform(data, layer) {
    var stJson,
     rootNodeData;
    if (!data) return;

    rootNodeData = data.filter(function (elem, index, array) {
        return elem.layer == layer;
    });  // find root
    var root = rootNodeData[0];
    // transform root data to ST display json format
    stJson = {
        id: root.id,
        name: root.person.name,
        data: {
            person: root.person,
            team: root.team,
            layer: root.layer,
            lft: root.lft,
            rgt: root.rgt
        },
        children: []
    };

    traverseSubNode(data, [stJson]);
    return stJson;
}

function traverseSubNode (data, subNodeArray) {
    subNodeArray.forEach(function(elem) {
       var currentLayerDataArray = data.filter(function (e) {
            return elem.data.layer == e.layer - 1 && elem.data.lft < e.lft && elem.data.rgt > e.rgt;
        }); // find subtree child nodes
        if (currentLayerDataArray.length == 0) return;
        // transform subtree children data to ST display json data format
        elem.children = currentLayerDataArray.map(function(elem) {
            var json = {
                id: elem.id,
                name: elem.person.name,
                data: {
                    person: elem.person,
                    team: elem.team,
                    layer: elem.layer,
                    lft: elem.lft,
                    rgt: elem.rgt
                },
                children: []
            };
            return json;
        });
        traverseSubNode(data, elem.children);
    });
}
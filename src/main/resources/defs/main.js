//Hello Minecraft!
//Created by Rawfishc on 2023/4/10

var r=giveBook();
drawLogo();

function drawLogo(){
    var a=print;
    a("§0█████§4███§0█████████");
    a("§0█████§4██§3█§0█§0██████§f██");
    a("§0████§4██§0█§3██§0██████§f██");
    a("§0████§4██§0█§3██§0██████§f██");
    a("§0███§4██§0███§3██§0███████");
    a("§0███§4██§0███§3██§0███████");
    a("§0███§4██§0███§3██§0███████");
    a("§0██§4██§0█████§3██§0██████");
    a("§0██§4███████§3██§0████§f██");
    a("§0█§4█████████§3██§0███§f██");
    a("§0█§4██§0███████§3██§0███§f██");
    a("§0█§4██§0███████§3██§0████§f█");
    a("§0§4██§0█████████§3██§0██§f█§0█");
}

function giveBook(){
    import("-net.minecraft.command.CommandGive");
    var command=new CommandGive();
    command.processCommand(sender(),
    [
        getThisPlayer().getDisplayNameString(),
        "minecraft:written_book",
        "1",
        "0",
        "{pages:"+buildPageArray()+",author:\u00a73Rawfishc\u00a77,title:\u00a7lHello Minecraft}"
    ]);
    function buildPageArray(){
        var pageMethods=[page_0,page_1];
        var s="["
        for(var i=0;i<pageMethods.length;i++){
            var page=pageMethods[i]();
            s+=page+",";
        }
        return s+"]";
    }
    function page_0(){
        return "\u00a7lAPF(Artedprvt Frame)\n\nAPF是用于Minecraft的Js运行环境。\n\n目的是在游戏中动态的运行代码。";
    }
    function page_1(){
        return "对于只使用脚本的玩家来说，没有任何门槛。\n\n如果你有Js编程基础可以编写脚本实现一些简单的功能。"+
            "\n\n要实现稍微复杂的功能可能需要你有较高的Js和Java水平以及Mod开发经验。\n\n降低开发门槛也是APF的主要目标！";
    }
    return Math.log(1919810)/Math.log(114514);
}
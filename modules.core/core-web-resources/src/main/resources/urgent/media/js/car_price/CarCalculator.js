// JScript File
//------------------ Common ------------------------------
//用作客户端保存车型数据
var cars = new Array();


function SetSpanValueByBrowerType(control, value) {
    if (Prototype.Browser.IE) {
        $(control).innerText = value;
    }
    else {
        $(control).innerHTML = value;
    }
}

function carModul(id, name, carreferprice) {
    this.id = id;
    this.name = name;
    this.carreferprice = carreferprice;
}


function formatCurrency(num) {
    num = num.toString().replace(/\$|\,/g, '');

    if (isNaN(num)) num = "0";

    sign = (num == (num = Math.abs(num)));
    num = Math.floor(num * 100 + 0.50000000001);
    //cents = num%100;
    num = Math.floor(num / 100).toString();

    //if(cents<10)
    //cents = "0" + cents;

    for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3) ; i++)
        num = num.substring(0, num.length - (4 * i + 3)) + ',' + num.substring(num.length - (4 * i + 3));

    return (((sign) ? '' : '-') + num);
    //return (((sign)?'':'-') + '$' + num + '.' + cents);
}

function formatCurrencyWToK(num) {
    num = num.toString().replace(/\$|\,/g, '');

    if (isNaN(num)) num = "0";

    sign = (num == (num = Math.abs(num)));
    num = Math.floor(num * 10000 + 0.50000000001).toString();

    //for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
    //    num = num.substring(0,num.length-(4*i+3)) + ',' + num.substring(num.length-(4*i+3));

    return (((sign) ? '' : '-') + num);
}
//  End -->

function checkMoneyValidation() {
    // if($('txtMoney').value.length == 0)
    // {
    //    alert("请输入车辆购置价格!");
    //    window.setTimeout( function(){$('txtMoney').focus(); }, 0);
    //    return false;
    // }
    if (isNaN($('txtMoney').value)) {
        alert("请输入数字!");
        $('txtMoney').value = "";
        window.setTimeout(function () { $('txtMoney').focus(); }, 0);
        return false;
    }

    if ($('txtMoney').value == "0") {
        $('divErrorInfo').style.display = '';
        $('divCalcInfo').style.display = 'none';
    }
    else {
        $('divErrorInfo').style.display = 'none';
        $('divCalcInfo').style.display = '';
    }

    return true;
}

function checkChePaiValidation() {
    if ($('txtChePai').value.length == 0) {
        alert("请输入上牌费用!");
        window.setTimeout(function () { $('txtChePai').focus(); }, 0);
        return false;
    }
    if (isNaN($('txtChePai').value)) {
        alert("请输入数字!");
        $('txtChePai').value = "";
        window.setTimeout(function () { $('txtChePai').focus(); }, 0);
        return false;
    }

    if ($('txtChePai').value == 0) {
        $('txtChePai').value = "500";
    }

    return true;
}

//验证车船使用费的输入
function ValidateVehicleAndVesselTax() {
    if ($("txtVehicleTax").value.length == 0) {
        alert("请输入车船使用费!");
        window.setTimeout(function () { $("txtVehicleTax").focus(); }, 0);
        return false;
    }
    if (isNaN($("txtVehicleTax").value)) {
        alert("请输入数字!");
        $("txtVehicleTax").value = "";
        window.setTimeout(function () { $("txtVehicleTax").focus(); }, 0);
        return false;
    }
    return true;
}

//------------------ Common ------------------------------


function calcCommonTotal() {
    var commonTotal = 0;

    if ($('chkTPL').checked) {
        commonTotal += parseFloat($('txtTPL').value);
    }
    if ($('chkCarDamage').checked) {
        commonTotal += parseFloat($('txtCarDamage').value);
    }
    if ($('chkCarTheft').checked) {
        commonTotal += parseFloat($('txtCarTheft').value);
    }
    if ($('chkBreakageOfGlass').checked) {
        commonTotal += parseFloat($('txtBreakageOfGlass').value);
    }
    if ($('chkSelfignite').checked) {
        commonTotal += parseFloat($('txtSelfignite').value);
    }
    if ($('chkAbatement').checked) {
        commonTotal += parseFloat($('txtAbatement').value);
    }
    if ($('chkBlameless').checked) {
        commonTotal += parseFloat($('txtBlameless').value);
    }
    if ($('chkLimitofPassenger').checked) {
        commonTotal += parseFloat($('txtLimitOfPassenger').value);
    }
    if ($('chkCarDamageDW').checked) {
        commonTotal += parseFloat($('txtCarDamageDW').value);
    }

    $('txtCommonTotal').value = Math.round(commonTotal);
}

function calcCompulsory() {
    var rdoCompulsory = document.getElementsByName("rdoCompulsory");
    for (i = 0; i < rdoCompulsory.length; i++) {
        if (rdoCompulsory[i].checked) {
            $('txtCompulsory').value = rdoCompulsory[i].value;
            break;
        }
    }
}

function calcTPL() {
    var rdoCompulsory = document.getElementsByName("rdoCompulsory");
    if ((rdoCompulsory[0].checked || rdoCompulsory[1].checked) && $('chkTPL').checked) {
        $('txtTPL').className = "";
        //$('txtTPL').disabled = false;

        var rdoTPL = document.getElementsByName("rdoTPL");
        if (rdoCompulsory[0].checked) {
            if (rdoTPL[0].checked)
                $('txtTPL').value = "516";
            if (rdoTPL[1].checked)
                $('txtTPL').value = "746";
            if (rdoTPL[2].checked)
                $('txtTPL').value = "924";
            if (rdoTPL[3].checked)
                $('txtTPL').value = "1252";
            if (rdoTPL[4].checked)
                $('txtTPL').value = "1630";
        }
        else if (rdoCompulsory[1].checked) {
            if (rdoTPL[0].checked)
                $('txtTPL').value = "478";
            if (rdoTPL[1].checked)
                $('txtTPL').value = "674";
            if (rdoTPL[2].checked)
                $('txtTPL').value = "821";
            if (rdoTPL[3].checked)
                $('txtTPL').value = "1094";
            if (rdoTPL[4].checked)
                $('txtTPL').value = "1425";
        }
    }
    else {
        $('txtTPL').value = "";
        $('txtTPL').className = "disablebox";
        //$('txtTPL').disabled = true;
    }
}

function calcCarDamage() {

    if ($('chkCarDamage').checked) {

        $('txtCarDamage').className = "";
        //$('txtCarDamage').disabled = false;
        $('txtCarDamage').value = Math.round($('txtMoney').value * 0.01088 + (document.getElementsByName("rdoCompulsory")[1].checked ? 550 : 459));
    }
    else {
        $('txtCarDamage').value = "";
        //$('txtCarDamage').disabled = true;
        $('txtCarDamage').className = "disablebox";
    }
}

function calcCarTheft() {

    if ($('chkCarTheft').checked && $('chkCarDamage').checked) {
        $('txtCarTheft').className = "";
        //$('txtCarTheft').disabled = false;
        if (document.getElementsByName("rdoCompulsory")[1].checked)
            $('txtCarTheft').value = Math.round($('txtMoney').value * 0.00374 + 119);
        else
            $('txtCarTheft').value = Math.round($('txtMoney').value * 0.004505 + 102);
    }
    else {
        $('chkCarTheft').checked = false;
        $('txtCarTheft').value = "";
        //$('txtCarTheft').disabled = true;
        $('txtCarTheft').className = "disablebox";
    }
}

function calcBreakageOfGlass() {

    if ($('chkBreakageOfGlass').checked) {

        $('txtBreakageOfGlass').className = "";
        //$('txtBreakageOfGlass').disabled = false;

        var rdoBreakageOfGlass = document.getElementsByName("rdoBreakageOfGlass");
        if (rdoBreakageOfGlass[0].checked)
            $('txtBreakageOfGlass').value = Math.round($('txtMoney').value * 0.0025);
        if (rdoBreakageOfGlass[1].checked)
            $('txtBreakageOfGlass').value = Math.round($('txtMoney').value * 0.0015);
    }
    else {
        $('txtBreakageOfGlass').value = "";
        //$('txtBreakageOfGlass').disabled = true;
        $('txtBreakageOfGlass').className = "disablebox";
    }
}

function calcSelfignite() {

    if ($('chkSelfignite').checked) {

        $('txtSelfignite').className = "";
        //$('txtSelfignite').disabled = false;
        $('txtSelfignite').value = Math.round($('txtMoney').value * 0.0015);
    }
    else {
        $('txtSelfignite').value = "";
        // $('txtSelfignite').disabled = true;
        $('txtSelfignite').className = "disablebox";
    }
}

function calcAbatement() {

    if ($('chkCarDamage').checked && $('chkTPL').checked && $('chkAbatement').checked) {

        $('txtAbatement').className = "";
        //$('txtAbatement').disabled = false;
        var total = parseInt($('txtCarDamage').value) + parseInt($('txtTPL').value);
        $('txtAbatement').value = Math.round(total * 0.2);
    }
    else {
        $('chkAbatement').checked = false;

        $('txtAbatement').value = "";
        //$('txtAbatement').disabled = true;
        $('txtAbatement').className = "disablebox";
    }
}

function calcBlameless() {

    if ($('chkTPL').checked && $('chkBlameless').checked) {

        $('txtBlameless').className = "";
        //$('txtBlameless').disabled = false;
        $('txtBlameless').value = Math.round($('txtTPL').value * 0.2);
    }
    else {
        $('chkBlameless').checked = false;

        $('txtBlameless').value = "";
        //$('txtBlameless').disabled = true;
        $('txtBlameless').className = "disablebox";
    }
}

function calcLimitofPassenger() {
    if ($('chkLimitofPassenger').checked) {

        $('txtLimitOfPassenger').className = "";
        //$('txtLimitOfPassenger').disabled = false;
        //不允许为空 如果没有值 则赋给默认值 50
        if ($('txtLimitOfPassenger').value == 0) {
            $('txtLimitOfPassenger').value = "50";
        }
        else {
            //如果 值的范围不在 默认值的倍数 内 则提示错误 重置为默认值
            if ($('txtLimitOfPassenger').value % 50 != 0) {
                alert("车上责任险的缴费额 = 每人的保费50元 * 车辆的实际座位数");
                $('txtLimitOfPassenger').value = "50";
            }
        }
    }
    else {
        $('txtLimitOfPassenger').value = "";
        //$('txtLimitOfPassenger').disabled = true;
        $('txtLimitOfPassenger').className = "disablebox";
    }
}

function calcCarDamageDW() {

    if ($('chkCarDamage').checked && $('chkCarDamageDW').checked) {

        $('txtCarDamageDW').className = "";
        //$('txtCarDamageDW').disabled = false;

        var rdoCarDamageDW = document.getElementsByName("rdoCarDamageDW");
        if ($('txtMoney').value < 300000) {
            if (rdoCarDamageDW[0].checked)
                $('txtCarDamageDW').value = "400";
            if (rdoCarDamageDW[1].checked)
                $('txtCarDamageDW').value = "570";
            if (rdoCarDamageDW[2].checked)
                $('txtCarDamageDW').value = "760";
            if (rdoCarDamageDW[3].checked)
                $('txtCarDamageDW').value = "1140";
        }
        else if ($('txtMoney').value > 500000) {
            if (rdoCarDamageDW[0].checked)
                $('txtCarDamageDW').value = "850";
            if (rdoCarDamageDW[1].checked)
                $('txtCarDamageDW').value = "1100";
            if (rdoCarDamageDW[2].checked)
                $('txtCarDamageDW').value = "1500";
            if (rdoCarDamageDW[3].checked)
                $('txtCarDamageDW').value = "2250";
        }
        else {
            if (rdoCarDamageDW[0].checked)
                $('txtCarDamageDW').value = "585";
            if (rdoCarDamageDW[1].checked)
                $('txtCarDamageDW').value = "900";
            if (rdoCarDamageDW[2].checked)
                $('txtCarDamageDW').value = "1170";
            if (rdoCarDamageDW[3].checked)
                $('txtCarDamageDW').value = "1780";
        }
    }
    else {
        $('chkCarDamageDW').checked = false;

        $('txtCarDamageDW').value = "";
        //$('txtCarDamageDW').disabled = true;
        $('txtCarDamageDW').className = "disablebox";
    }
}



//Ajax
function getSerialByMasterBrandID(id) {
    // showLoading("true");

    $("ddlChexing").options.length = 1;
    $("ddlChekuan").options.length = 1;

    $('txtMoney').value = 0;
    if (id == -1 || id == -2) {
        return;
    }

    var myCarTypeOptions = {
        parameters: "bsid=" + id,
        method: "get",
        asynchronous: false,
        onSuccess: function (res) {
            var carTypeData = eval("(" + res.responseText + ")");
            for (var i = 0; i < carTypeData.length; i++) {
                var cartype = carTypeData[i];
                $("ddlChexing").options.add(new Option(cartype.Name, cartype.ID));
            }

            //   showLoading("false");
        }
    }

    new Ajax.Request(webSiteBaseUrl + "ajaxnew/GetSerialByMasterBrand.ashx?type=json", myCarTypeOptions);
}

function getCarByCsID(id) {
    // showLoading("true");

    //清空缓存车型数据
    cars.length = 0;
    var ddlChekuan = $("ddlChekuan")
    var groupItem = ddlChekuan.firstChild;
    while (groupItem) {
        ddlChekuan.removeChild(groupItem);
        groupItem = ddlChekuan.firstChild;
    }
    var oItem = document.createElement("OPTION");
    oItem.setAttribute("value", -1);
    oItem.appendChild(document.createTextNode("选择车款"));
    $("ddlChekuan").appendChild(oItem);


    $('txtMoney').value = 0;

    if (id == -1) {
        return;
    }

    var myoptions = {
        parameters: "csid=" + id,
        method: "get",
        asynchronous: false,
        onSuccess: function (res) {
            var myData = eval("(" + res.responseText + ")");

            var yearList = new Object();
            yearList["YearList"] = new Array();
            for (var i = 0; i < myData.length; i++) {
                var car = myData[i];
                if (!yearList[car.YearType]) {
                    yearList[car.YearType] = new Array();
                    yearList["YearList"].push(car.YearType);
                }

                yearList[car.YearType].push(car);
            }
            for (var i = 0; i < yearList["YearList"].length; i++) {
                var carYear = yearList["YearList"][i];
                var carsInYear = yearList[carYear];
                if (yearList["YearList"].length > 1) {
                    var optionItem = document.createElement("OPTGROUP");
                    optionItem.label = carYear + "款";
                    optionItem.style.fontStyle = "normal";
                    optionItem.style.background = "#CCCCCC";
                    optionItem.style.textAlign = "center";
                    $("ddlChekuan").appendChild(optionItem);
                }
                for (var j = 0; j < yearList[carYear].length; j++) {
                    var car = yearList[carYear][j];
                    var oItem = document.createElement("OPTION");
                    oItem.setAttribute("value", car.ID);
                    oItem.appendChild(document.createTextNode(car.Name));
                    $("ddlChekuan").appendChild(oItem);
                    cars[cars.length] = new carModul(car.ID, car.Name, car.CarReferPrice);
                }
            }

            //数组构建索引
            var carLength = cars.length;
            for (var j = 0; j < carLength; j++) {
                cars[cars[j].id] = cars[j];
            }
            //     showLoading("false");

        }
    }

    new Ajax.Request(webSiteBaseUrl + "ajaxnew/GetCarByCsID.aspx?type=json", myoptions);
}

/*
*    ForDight(Dight,How):数值格式化函数，Dight要
*    格式化的  数字，How要保留的小数位数。
*/
function ForDight(Dight, How) {
    Dight = Math.round(Dight * Math.pow(10, How)) / Math.pow(10, How);
    return Dight;
}

//车船使用税减免信息
var vehicleAndVesselTaxRelief;

//-----------------------------------------
function resetPrice(carId) {
    //  showLoading("true");

    $('txtMoney').value = 0;
    //清空车船税减免信息
    vehicleAndVesselTaxRelief = "";
    InitControl();
    if (carId == -2) {
        return;
    }

    if (cars.length > 0) {
        if (-1 != carId) {
            var carReferPrice = cars[carId].carreferprice;
            if (priveRequest && priveRequest != "")
            { $('txtMoney').value = formatCurrencyWToK(priveRequest); }
            else
            { $('txtMoney').value = formatCurrencyWToK(carReferPrice); }

            //通过车型选择的
            //自动匹配排量，座位数量，玻璃单独破碎险的进口或国产。
            //购置税中排量对应车型库中的排量，车船使用税和交强险中使用的座位数对应基本性能中的成员人数（含司机）；
            //如果库中的数据为空则使用默认设置。
            var myoptions = {
                parameters: "carId=" + carId,
                method: "get",
                asynchronous: false,
                onSuccess: function (res) {
                    var myData = eval("(" + res.responseText + ")");

                    //var rdoAcquisitionTax = document.getElementsByName("rdoAcquisitionTax");
                    //车船使用税
                    var rdoVehicleTax = document.getElementsByName("rdoVehicleTax");
                    //交强险
                    var rdoCompulsory = document.getElementsByName("rdoCompulsory");
                    //玻璃单独破碎险
                    var rdoBreakageOfGlass = document.getElementsByName("rdoBreakageOfGlass");
                    /*
                    if(myData[0].engine != "0")
                    {
                        var pl = myData[0].engine/1000;
                        if(pl > 1.6)
                            rdoAcquisitionTax[1].checked = true;
                        else
                            rdoAcquisitionTax[0].checked = true;
                    }
                    else
                    {
                        rdoAcquisitionTax[0].checked = true;
                    }
                    */
                    if (myData[0].seatNum != "0") {
                        var nRS = parseInt(myData[0].seatNum);

                        if (nRS < 6)
                            rdoCompulsory[0].checked = true;
                        else
                            rdoCompulsory[1].checked = true;

                    }
                    else {
                        rdoCompulsory[0].checked = true;
                    }

                    if (myData[0].isGuoChan != "") {
                        if (myData[0].isGuoChan == "False")
                            rdoBreakageOfGlass[0].checked = true;
                        else
                            rdoBreakageOfGlass[1].checked = true;
                    }
                    else {
                        rdoBreakageOfGlass[1].checked = true;
                    }

                    //根据排量选择车船税的级别
                    var displacement = myData[0].exhaustforfloat;
                    var vehicleAndVesselTaxInfo = GetVehicleAndVesselTaxInfo(displacement);
                    var radioGroup = document.getElementsByName("rdoVehicleTax");
                    for (var i = 0; i < radioGroup.length; i++) {
                        radioGroup[i].disabled = "disabled";
                        if (radioGroup[i].value == vehicleAndVesselTaxInfo.Level) {
                            radioGroup[i].checked = "checked";
                        }
                    }

                    //车船使用税减免信息
                    vehicleAndVesselTaxRelief = myData[0].traveltax;
                    //计算车船使用税
                    CalculateVehicleAndVesselTax();

                    setCalcToolUrl(carId);

                    //        showLoading("false");

                }
            }

            new Ajax.Request(webSiteBaseUrl + "ajaxnew/GetCarInfoForCalcTools.aspx?type=json", myoptions);
        }
    }
}

//层 隐藏显示
function showOrHideDiv(imgID) {
    var imgClose = document.getElementById('imgClose');
    var imgOpen = document.getElementById('imgOpen');

    if (imgClose && imgOpen) {
        var showDivs = false;

        if ('imgClose' == imgID) {
            imgClose.style.display = 'none';
            imgOpen.style.display = '';
            showDivs = false;
        }
        if ('imgOpen' == imgID) {
            imgClose.style.display = '';
            imgOpen.style.display = 'none';
            showDivs = true;
        }

        for (var i = 0; i < 9; i++) {
            var divCommonTotals = document.getElementById('divCommonTotals' + i);

            divCommonTotals.style.display = "none";
            if (showDivs == true)
                divCommonTotals.style.display = "";
        }
    }
}

function setSelected(oSel, val) {
    //alert(val);
    if (val != "0" || val != "")
        oSel.value = val;
    /*
  var len = oSel.options.length;
  for(var i=0; i<len; i++)
  {
    if (oSel.options[i].value == val)
    {
      oSel.selectedIndex = i;
      break;
    }
  } */
}



function setCalcToolUrl(carId) {

    var compulsoryIdx = 0;
    carId = parseInt(carId);
    var ulEle = $("calcTools");
    if (!ulEle)
        return;
    var aLinks = ulEle.getElementsByTagName("A");
    for (i = 0; i < aLinks.length; i++) {
        var aLink = aLinks[i];
        var url = aLink.href;
        if (url.length == 0)
            continue;
        var paraIndex = url.indexOf("?");
        if (paraIndex > 0)
            url = url.substring(0, paraIndex);

        var rdoCompulsory = document.getElementsByName("rdoCompulsory");
        if (rdoCompulsory[0].checked)
            compulsoryIdx = 0;
        else
            compulsoryIdx = 1;
        if (carId > 0) {
            if (aLink.id == "calcInsurance")
                url += "?CarPrice=" + cars[carId].carreferprice * 10000 + "&CompulsoryIdx=" + compulsoryIdx;
            else
                url += "?carid=" + carId;
        }
        aLink.href = url;
    }
}

//=-=======弹出提示=============================
var preText = null;
//function $(i){return document.getElementById(i)}
function showjs(j) {
    if (preText)
        preText.style.display = 'none';
    preText = $(j);
    $(j).style.display = '';
}
function closex(t) { $(t).style.display = 'none'; }


//车船使用税信息
var vehicleAndVesselTaxInfos = {
    1: {
        Level: 1,
        MinDisplacement: 0,
        MaxDisplacement: 1.0,
        DisplacementDescription: "1.0L(含)以下",
        Tax: 300
    },
    2: {
        Level: 2,
        MinDisplacement: 1.0,
        MaxDisplacement: 1.6,
        DisplacementDescription: "1.0-1.6L(含)",
        Tax: 420,
        IsDefault: true
    },
    3: {
        Level: 3,
        MinDisplacement: 1.6,
        MaxDisplacement: 2.0,
        DisplacementDescription: "1.6-2.0L(含)",
        Tax: 480
    },
    4: {
        Level: 4,
        MinDisplacement: 2.0,
        MaxDisplacement: 2.5,
        DisplacementDescription: "2.0-2.5L(含)",
        Tax: 900
    },
    5: {
        Level: 5,
        MinDisplacement: 2.5,
        MaxDisplacement: 3.0,
        DisplacementDescription: "2.5-3.0L(含)",
        Tax: 1920
    },
    6: {
        Level: 6,
        MinDisplacement: 3.0,
        MaxDisplacement: 4.0,
        DisplacementDescription: "3.0-4.0L(含)",
        Tax: 3480
    },
    7: {
        Level: 7,
        MinDisplacement: 4.0,
        MaxDisplacement: Number.MAX_VALUE,
        DisplacementDescription: "4.0L以上",
        Tax: 5280
    }
};

//根据排量获得车船使用税信息
function GetVehicleAndVesselTaxInfo(dispplacement) {
    for (var taxLevel in vehicleAndVesselTaxInfos) {
        if (dispplacement > vehicleAndVesselTaxInfos[taxLevel].MinDisplacement
            && dispplacement <= vehicleAndVesselTaxInfos[taxLevel].MaxDisplacement) {
                return vehicleAndVesselTaxInfos[taxLevel];
            }
    }
}

//获得车船使用税提示信息
function GetVehicleAndVesselTaxMessage() {
    var messageBuffer = [];
    var counter = 0;
    messageBuffer.push("各省不统一，以北京为例(单位/年)。");
    messageBuffer.push("<br />");
    for (var taxLevel in vehicleAndVesselTaxInfos) {
        counter++;
        var currentVehicleAndVesselTaxInfo = vehicleAndVesselTaxInfos[taxLevel];
        messageBuffer.push(currentVehicleAndVesselTaxInfo.DisplacementDescription);
        messageBuffer.push(vehicleAndVesselTaxRelief == "减半" ? currentVehicleAndVesselTaxInfo.Tax / 2 : currentVehicleAndVesselTaxInfo.Tax);
        messageBuffer.push("元；");
        if (counter % 2 == 0) {
            messageBuffer.push("<br />");
        }
    }
    messageBuffer.push("不足一年按当年剩余月算。");
    return messageBuffer.join("");
}

//计算车船使用税
function CalculateVehicleAndVesselTax() {
    var radioGroup = document.getElementsByName("rdoVehicleTax");
    for (var i = 0; i < radioGroup.length; i++) {
        if (radioGroup[i].checked) {
            var taxLevel = radioGroup[i].value;
        }
    }

    var vehicleAndVesselTaxValue = vehicleAndVesselTaxInfos[taxLevel].Tax;
    var vehicleAndVesselTaxMessage = GetVehicleAndVesselTaxMessage();
    //车船使用税一般只能缴纳当年的，按月计算
    vehicleAndVesselTaxValue = vehicleAndVesselTaxValue * (12 - new Date().getMonth()) / 12;
    //计算车船使用税减免
    if (vehicleAndVesselTaxRelief == "免征") {
        vehicleAndVesselTaxValue = 0;
        vehicleAndVesselTaxMessage = "根据国家政策，该车无需缴纳车船税。";
    }
    else if (vehicleAndVesselTaxRelief == "减半") {
        vehicleAndVesselTaxValue = vehicleAndVesselTaxValue / 2;
        vehicleAndVesselTaxMessage = "该车享受车船税减半优惠。<br />" + vehicleAndVesselTaxMessage;
    }

    $('txtVehicleTax').value = Math.ceil(vehicleAndVesselTaxValue);
    document.getElementById("tdVehicleAndVesselTaxMessage").innerHTML = vehicleAndVesselTaxMessage;
}

//初始化车船使用税控件
function InitVehicleAndVesselTaxControl(controlContainer) {
    var buffer = [];
    var counter = 0;
    for (var taxLevel in vehicleAndVesselTaxInfos) {
        buffer.push("<input type=\"radio\" name=\"rdoVehicleTax\" value=\"");
        buffer.push(taxLevel);
        buffer.push("\" onclick=\"CarCalculation()\"");
        if (vehicleAndVesselTaxInfos[taxLevel].IsDefault) {
            buffer.push(" checked=\"checked\"");
        }
        buffer.push(" />");
        buffer.push(vehicleAndVesselTaxInfos[taxLevel].DisplacementDescription);
        if (++counter % 2 == 0) {
            buffer.push("<br />");
        }
    }
    document.getElementById(controlContainer).innerHTML = buffer.join("");
    document.getElementById("tdVehicleAndVesselTaxMessage").innerHTML = GetVehicleAndVesselTaxMessage();
};
import React from "react";
import {Navigate, Outlet} from "react-router-dom";

function DealerRoutes({role}){
    if (role === "" || role ==="USER" || role === null) {
        alert("딜러만 등록이 가능합니다.")
    }
    return (
        //<Outlet/> -> 내 안에 있는 컴포넌트 실행 route dom이 가지고 있음
        role === "DEALER" ? <Outlet/> : <Navigate to="/login"/>
    );
}
export default DealerRoutes;
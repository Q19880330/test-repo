import React from "react";
import {Navigate, Outlet} from "react-router-dom";

function AdminRoutes({role}) {
    if (role === "" || role ===null || role === "DEALER"|| role === "USER") {
        alert("권한이 없습니다.")
    }
    return (
        //<Outlet/> -> 내 안에 있는 컴포넌트 실행 route dom이 가지고 있음
        role === "ADMIN" ? <Outlet/> : <Navigate to="/"/>
    );
}

export default AdminRoutes;
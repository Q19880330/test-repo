import React from "react";
import AdminMemberList from "./AdminMemberList";
import AdminDealerRegList from "./AdminDealerRegList";

function AdminMain() {
    return (
        <div>
            <h3>관리자 페이지입니다.</h3>
            <div className="flex">
               <AdminMemberList/>
               <AdminDealerRegList/>
            </div>
        </div>
    );
}

export default AdminMain;
import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { BrowserRouter } from "react-router-dom";
import { Provider } from "react-redux"; // Provider 임포트
import App from "./App.jsx";
import { store } from "./store/index.jsx";
import "./styles/global.css";
import "./index.css";

// 카카오 SDK 초기화 코드 추가
window.Kakao.init(import.meta.env.VITE_KAKAO_JAVASCRIPT_ID); // .env에 설정한 키 사용

// 우클릭 방지
document.addEventListener("contextmenu", (e) => e.preventDefault());

// Service Worker 업데이트 코드 추가
if ("serviceWorker" in navigator) {
  navigator.serviceWorker.getRegistrations().then(function (registrations) {
    for (let registration of registrations) {
      registration.update();
    }
  });
}

createRoot(document.getElementById("root")).render(
  <StrictMode>
    <BrowserRouter>
      <Provider store={store}>
        {" "}
        {/* Redux Provider로 전체 앱 감싸기 */}
        <App />
      </Provider>
    </BrowserRouter>
  </StrictMode>
);

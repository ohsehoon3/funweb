<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- ${ cookie.쿠키이름.value } 형태로 사용가능--%>
<%-- <c:if test="${ not empty cookie.id.value }"> --%>
<%-- 	<c:set var="id" value="${ cookie.id.value }" scope="session" /> --%>
<%-- </c:if> --%>
<!-- empty의 용도
	- null 체크
	- List의 요소 개수 0개 체크 -->
<header>
	<div id="login">
		<c:choose>
			<c:when test="${ not empty sessionScope.id }"> <!-- 세션 id가 있는가? -->
				${ sessionScope.id }님 반가워요~
				 | <a href="/member/logout">로그아웃</a>
			</c:when>
			<c:otherwise>
				<a href="/member/login">로그인</a>
				 | <a href="/member/join">회원가입</a>
			</c:otherwise>
		</c:choose>
	</div>
	<div class="clear"></div>
	<div id="logo">
		<a href="/"> <img src="/images/logo.gif" width="265"
			height="62" alt="Fun Web">
		</a>
	</div>
	<nav id="top_menu">
		<ul>
			<li><a href="/">HOME</a></li>
			<li><a href="/company/welcome">COMPANY</a></li>
			<li><a href="/chat/chat">CHATTING</a></li>
			<li><a href="/board/list">CUSTOMER CENTER</a></li>
			<li><a href="/mail">E-MAIL</a></li>
		</ul>
	</nav>
</header>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="post v-margin-20">
	<div class="font-large bold">
		<a href="${pageContext.request.contextPath}/post/${post.postId}" class="post-title">${post.title}</a>
	</div>
	<div>
		<fmt:formatDate value="${post.postedDt}" pattern="MMM dd yyyy" />
		by ${post.author}
	</div>
	<div class="post-body">
		<c:choose>
			<c:when test="${tease}">
				<div>${post.teaser}</div>
				<div>
					<a href="${pageContext.request.contextPath}/post/${post.postId}">Continue Reading</a>
				</div>
			</c:when>
			<c:otherwise>${post.body}</c:otherwise>
		</c:choose>
	</div>
	<div class="post-tags clearfix">
		<c:forEach items="${post.tags}" var="tag">
			<div class="post-tag">${tag.name}</div>
		</c:forEach>
	</div>
	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<div class="v-margin-10 control-group clearfix">
			<a href="<c:url value="/secure/post/edit/${post.postId}"  />"
				class="img edit-control"></a> <a
				href="<c:url value="/secure/post/delete/${post.postId}"  />"
				class="img delete-control"></a>
		</div>
	</sec:authorize>
</div>

package com.bee.sample.ch9.test.mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.AdditionalMatchers.lt;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.bee.sample.ch9.service.CreditSystemService;

@RunWith(MockitoJUnitRunner.class)
public class CreditServiceMockTest {

	@Test
	public void test() throws IOException {
		int userId = 10;

		// 创建mock对象
		CreditSystemService creditService = mock(CreditSystemService.class);

		// 模拟mock对象调用，传入任何int值都将返回100积分
		when(creditService.getUserCredit(eq(userId))).thenReturn(1000);

		// 实际调用
		int ret = creditService.getUserCredit(userId);

		//注释如下行，单元测试会失败
		creditService.getUserCredit(userId);

		// 比较期望值和返回值
		assertEquals(1000, ret);

		// 判断这个方式是否被调用过两次，不为两次则报错
		verify(creditService, times(2)).getUserCredit(eq(userId));
	}

	@Test
	public void test2() {
		int userId = 10;

		// 创建mock对象
		CreditSystemService creditService = mock(CreditSystemService.class);

		// 模拟mock对象调用
		when(creditService.getUserCredit(eq(userId))).thenReturn(1000);
		// 用户id 小于0 就报错
		when(creditService.getUserCredit(lt(0))).thenThrow(new IllegalArgumentException("userId不能小于0"));
		
		try{
			// 实际调用，先获取用户积分，然后增加10分
			int ret = creditService.getUserCredit(10);
			Assert.assertEquals(1000, ret);
			ret = creditService.getUserCredit(-1);
			Assert.fail();
		}catch(IllegalArgumentException e){
			//应该抛出异常
		}
	}

	/**
	 * 不支持某一方法的调用
	 */
	@Test
	public void test3() {
		// 创建mock对象
		List list = mock(List.class);

		doThrow(new UnsupportedOperationException("不支持clear方法调用")).when(list).clear();
		try {
			list.clear();
		}catch(UnsupportedOperationException x) {
			return ;
		}
		Assert.fail();
	}

	/**
	 * 验证调用顺序
	 */
	@Test
	public void test4() {
		int userId = 10;

		// 创建mock对象
		CreditSystemService creditService = mock(CreditSystemService.class);

		// 模拟mock对象调用
		when(creditService.getUserCredit(eq(userId))).thenReturn(1000);
		when(creditService.addCedit(eq(userId), anyInt())).thenReturn(true);

		// 实际调用，先获取用户积分，然后增加10分
		int ret = creditService.getUserCredit(userId);
		creditService.addCedit(userId, ret + 10);

		// 验证调用顺序，确保模拟对象先被调用 getUserCredit，然后再被调用addCredit方法
		InOrder inOrder = Mockito.inOrder(creditService);
		inOrder.verify(creditService).getUserCredit(userId);
		inOrder.verify(creditService).addCedit(userId, ret + 10);
	}
}

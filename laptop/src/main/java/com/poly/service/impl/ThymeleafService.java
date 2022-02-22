package com.poly.service.impl;

import com.poly.DTO.CartDTO;
import com.poly.vo.AccountVO;
import com.poly.vo.DeliveryAddressVO;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Service
public class ThymeleafService {
    private static final String MAIL_TEMPLATE_BASE_NAME = "mail/MailMessages";
    private static final String MAIL_TEMPLATE_PREFIX = "/templates/";
    private static final String MAIL_TEMPLATE_SUFFIX = ".html";
    private static final String UTF_8 = "UTF-8";

    private static final String TEMPLATE_NAME = "send-email-order";

    private static TemplateEngine templateEngine;

    static {
        templateEngine = emailTemplateEngine();
    }

    private static TemplateEngine emailTemplateEngine() {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(htmlTemplateResolver());
        templateEngine.setTemplateEngineMessageSource(emailMessageSource());
        return templateEngine;
    }

    private static ResourceBundleMessageSource emailMessageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(MAIL_TEMPLATE_BASE_NAME);
        return messageSource;
    }

    private static ITemplateResolver htmlTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix(MAIL_TEMPLATE_PREFIX);
        templateResolver.setSuffix(MAIL_TEMPLATE_SUFFIX);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(UTF_8);
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    public String getContent(AccountVO accountVO, CartDTO cartDTO, DeliveryAddressVO deliveryAddressVO, String description) {
        final Context context = new Context();
        context.setVariable("name", deliveryAddressVO.getName());
        context.setVariable("nameAccount", accountVO.getFullName());
        context.setVariable("project_name", "King Of Computer");
        context.setVariable("email", accountVO.getEmail());
        context.setVariable("address", deliveryAddressVO.getAddress());
        context.setVariable("phone",deliveryAddressVO.getPhone());
        context.setVariable("listProduct",cartDTO.getListCartItem());
        context.setVariable("totalPrice",cartDTO.getTotalPrice());
        context.setVariable("description",description);
        return templateEngine.process(TEMPLATE_NAME, context);
    }
}

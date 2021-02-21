package br.com.renanfretta.ve.votoeletronico.configs.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import br.com.renanfretta.ve.votoeletronico.enums.MessagesPropertyEnum;

@Component
public class MessagesProperty {

	@Autowired
	private MessageSource messageSource;
	
	public String getMessage(MessagesPropertyEnum messagesPropertyEnum) {
		return messageSource.getMessage(messagesPropertyEnum.getKey(), null, LocaleContextHolder.getLocale());
	}
	
	public String getMessage(MessageSourceResolvable resolvable) {
		return messageSource.getMessage(resolvable, LocaleContextHolder.getLocale());
	}
	
}
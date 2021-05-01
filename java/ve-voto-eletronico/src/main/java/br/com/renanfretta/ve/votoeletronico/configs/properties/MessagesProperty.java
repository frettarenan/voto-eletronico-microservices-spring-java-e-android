package br.com.renanfretta.ve.votoeletronico.configs.properties;

import br.com.renanfretta.ve.votoeletronico.enums.MessagesPropertyEnum;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessagesProperty {

	private final MessageSource messageSource;

	public MessagesProperty(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public String getMessage(MessagesPropertyEnum messagesPropertyEnum) {
		return messageSource.getMessage(messagesPropertyEnum.getKey(), null, LocaleContextHolder.getLocale());
	}
	
	public String getMessage(MessageSourceResolvable resolvable) {
		return messageSource.getMessage(resolvable, LocaleContextHolder.getLocale());
	}
	
}
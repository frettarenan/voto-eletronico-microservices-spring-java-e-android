package br.com.renanfretta.ve.commons.integracoes.userinfo.users;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoApiUsersFindByCpfOutput implements Serializable {

	private static final long serialVersionUID = 3340055607438619221L;

	private String status;

}

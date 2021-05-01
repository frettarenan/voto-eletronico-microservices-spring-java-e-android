package br.com.renanfretta.ve.commons.integracoes.userinfo.users;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoApiUsersFindByCpfOutput implements Serializable {

	private static final long serialVersionUID = 3340055607438619221L;

	private String status;

}

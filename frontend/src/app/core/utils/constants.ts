export class RoleConstants {
  public static readonly applicationRoles: RoleDefinition[] = [
    {systemName: 'admin', displayName: 'Администратор'},
    {systemName: 'user', displayName: 'Пользователь'}
  ];
  public static readonly admin: RoleDefinition = RoleConstants.applicationRoles[0];
  public static readonly user: RoleDefinition = RoleConstants.applicationRoles[1];
}

export type RoleDefinition = {
  systemName: string;
  displayName: string;
}

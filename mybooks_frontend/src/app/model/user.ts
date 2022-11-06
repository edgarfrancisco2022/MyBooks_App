export class User {

  public firstName: string;
  public lastName: string;
  public username: string;
  public email: string;
  public profileImageUrl: string;
  public password: string;
  public lastLoginDate: Date;
  public displayLastLogin: Date;
  public joinDate: Date;
  public role: string;
  public authorities: [];
  public isActive: boolean;
  public isLocked: boolean;

  constructor() {
    this.firstName = '';
    this.lastName = '';
    this.username = '';
    this.email = '';
    this.profileImageUrl = '';
    this.password = null;
    this.lastLoginDate = null;
    this.displayLastLogin = null;
    this.joinDate = null;
    this.role = '';
    this.authorities = [];
    this.isActive = false;
    this.isLocked = false;
  }

}

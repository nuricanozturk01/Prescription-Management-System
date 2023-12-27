export class UserDTO {
  private readonly fullName: string;
  private readonly identityNumber: string;

  constructor(fullName: string, identityNumber: string) {
    this.fullName = fullName;
    this.identityNumber = identityNumber;
  }

  public getFullName(): string {
    return this.fullName;
  }

  public getIdentityNumber(): string {
    return this.identityNumber;
  }
}

import { Amplify } from 'aws-amplify';
import '@aws-amplify/ui-react/styles.css';
import awsConfig from '../aws-exports';

export default function configureCognito() {
  const cognito = Amplify.configure(awsConfig);
}